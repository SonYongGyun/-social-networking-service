package kr.co.mz.sns.service.post;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.dto.post.PostSearchDto;
import kr.co.mz.sns.dto.post.SelectPostDto;
import kr.co.mz.sns.entity.post.PostEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.file.FileStorageService;
import kr.co.mz.sns.mapper.ModelMapperService;
import kr.co.mz.sns.repository.post.PostRepository;
import kr.co.mz.sns.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeService postLikeService;
    private final PostFileService postFileService;
    private final CommentService commentService;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;
    private final ModelMapperService modelMapperService;

    public List<SelectPostDto> findByKeyword(PostSearchDto postSearchDto, Pageable pageable) {
        return postRepository.findByContentContaining(postSearchDto.getKeyword(), pageable)
            .stream()
            .map(post -> modelMapper.map(post, SelectPostDto.class))
            .toList();
    }

    public List<SelectPostDto> findAll(Pageable pageable) {
        return postRepository.findAllWithPostFilesAndComments(pageable)
            .map(post -> modelMapper.map(post, SelectPostDto.class))
            .toList();
    }

    public GenericPostDto findByKey(Long seq) {
        return postRepository.findBySeqWithPostFilesAndComments(seq)
            .map(
                entity -> {
                    var postDto = modelMapper.map(entity, GenericPostDto.class);
                    postDto.setComments(commentService.findAllByPostSeq(seq));
                    return postDto;
                })
            .orElseThrow(() -> new NotFoundException("This post does not exist"));
    }

    @Transactional
    public GenericPostDto insert(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        var insertedPostDto = modelMapperService.mapAndActAndMap(
            Optional.of(genericPostDto).stream(),
            PostEntity.class,
            postRepository::save,
            GenericPostDto.class
        ).findFirst().orElseThrow();

        // insert POST_FILE into Database
        var savedFiles = postFileService.insertAll(multipartFiles, insertedPostDto);
        insertedPostDto.setPostFiles(savedFiles);

        // save files into Disk Drive
        fileStorageService.saveFile(multipartFiles, insertedPostDto);

        return insertedPostDto;
    }

    @Transactional
    public GenericPostDto updateByKey(GenericPostDto postDto) {
        return postRepository.findById(postDto.getSeq())
            .map(entity -> {
                entity.setContent(postDto.getContent());
                return entity;
            })
            .map(postRepository::save)
            .map(entity -> modelMapper.map(entity, GenericPostDto.class))
            .orElseThrow(() -> new NotFoundException("Post with ID " + postDto.getSeq() + "not found"));
    }

    @Transactional
    public GenericPostDto deleteByKey(Long seq) {
        var deletedPostFiles = postFileService.deleteAllByPostSeq(seq).stream().map(
            postFile -> {
                System.out.println(postFile.getPath() + File.separator
                    + seq + "_" + postFile.getName());
                fileStorageService.delete(
                    postFile.getPath() + File.separator
                        + seq + "_" + postFile.getName()
                );
                return postFile;
            }
        ).collect(Collectors.toSet());
        var deletedComments = commentService.deleteAllByPostSeq(seq);
        var deletedPostDto = postRepository.findById(seq)
            .map(entity -> {
                postRepository.delete(entity);
                return modelMapper.map(entity, GenericPostDto.class);
            })
            .orElseThrow(() -> new NotFoundException("Post with ID " + seq + "not found"));

        deletedPostDto.setComments(deletedComments);
        deletedPostDto.setPostFiles(deletedPostFiles);

        return deletedPostDto;
    }

    @Transactional
    public PostLikeDto like(Long seq) {
        postRepository.findById(seq)
            .map((entity) -> {
                entity.setLikes(entity.getLikes() + 1);
                return postRepository.save(entity);
            });

        return postLikeService.insert(seq);
    }
}