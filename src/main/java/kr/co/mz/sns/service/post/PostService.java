package kr.co.mz.sns.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.dto.post.PostSearchDto;
import kr.co.mz.sns.entity.post.PostEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.file.FileStorageService;
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
    private final ModelMapper modelMapper;

    public List<GenericPostDto> findByKeyword(PostSearchDto postSearchDto, Pageable pageable) {

        return postRepository.findByContentContaining(postSearchDto.getKeyword(), pageable)
            .stream()
            .map(post -> modelMapper.map(post, GenericPostDto.class))
            .toList();
    }

    public List<GenericPostDto> findAll(Pageable pageable) {

        return postRepository.findAll(pageable)
            .map(post -> modelMapper.map(post, GenericPostDto.class))
            .toList();
        // 구현 안된 메서드에 보통 아래의 익셉션을 날림
//        throw new UnsupportedOperationException();
    }

    public GenericPostDto findByKey(Long seq) {
        // Declarative Programming or Functional Programming
        return postRepository.findById(seq)
            .map(
                entity -> {
                    var postDto = modelMapper.map(entity, GenericPostDto.class);
                    postDto.setComments(commentService.findAllByPostSeq(seq));
                    postDto.setPostFiles(postFileService.findAllByPostSeq(seq));
                    return postDto;
                })
            .orElseThrow(() -> new NotFoundException("This post does not exist"));

        // Imperative Programming
//        var optionalPost = postRepository.findById(seq);
//        optionalPost.orElseThrow(() -> new NotFoundException("This post does not exist"));
//        return modelMapper.map(optionalPost.get(), PostDto.class);
    }

    @Transactional
    public GenericPostDto insert(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        // insert POST into Database
        var insertedPostDto = Optional.of(genericPostDto).stream()
            .map(dto -> modelMapper.map(dto, PostEntity.class))
            .map(postRepository::save)
            .map(entity -> modelMapper.map(entity, GenericPostDto.class))
            .findFirst().orElseThrow();

        // insert POST_FILE into Database
        var savedFiles = postFileService.insert(multipartFiles, insertedPostDto.getSeq());
        insertedPostDto.setPostFiles(savedFiles);

        // save files into Disk Drive
        FileStorageService.saveFile(multipartFiles, insertedPostDto);

        return insertedPostDto;
    }

    private <FIRST, SECOND, THIRD> Stream<THIRD> mapAndActAndMap(Stream<FIRST> stream, Class<SECOND> secondType,
        Function<SECOND, SECOND> function, Class<THIRD> thirdType) {
        return stream
            .map(first -> modelMapper.map(first, secondType))
            .map(function)
            .map(second -> modelMapper.map(second, thirdType));
    }

    @Transactional
    public GenericPostDto updateByKey(List<GenericPostFileDto> postFiles, GenericPostDto postDto) {
        // Declarative Programming
        var updatedPostDto = postRepository.findById(postDto.getSeq())
            .map(entity -> {
                entity.setContent(postDto.getContent());
                return entity;
            })
            .map(postRepository::save)
            .map(entity -> modelMapper.map(entity, GenericPostDto.class))
            .orElseThrow(() -> new NotFoundException("Post with ID " + postDto.getSeq() + "not found"));
        updatedPostDto.setPostFiles(postFileService.updateAllByPostSeq(postFiles, postDto.getSeq()));
        return updatedPostDto;

        // Imperative Programming
//        var optionalPost = postRepository.findById(seq);
//        var postEntity = optionalPost.orElseThrow(
//            () -> new NotFoundException("Post with ID " + seq + "not found", 1));
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var optionalUserEntity = userRepository.findByEmail(userDetails.getUsername());
//        postEntity.setUsers(
//            optionalUserEntity.orElseThrow(() -> new NotFoundException("User information not found", 1))
//        );
//        postEntity.setContent(postDto.getContent());
//        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Transactional
    public GenericPostDto deleteByKey(Long seq) {
        // Declarative 1
        var deletedPostDto = postRepository.findById(seq)
            .map(entity -> {
                postRepository.delete(entity);
                return modelMapper.map(entity, GenericPostDto.class);
            })
            .orElseThrow(() -> new NotFoundException("Post with ID " + seq + "not found"));
        deletedPostDto.setPostFiles(postFileService.deleteAllByPostSeq(seq));
        return deletedPostDto;
        // Declarative 2
//        postRepository.findById(seq)
//            .ifPresentOrElse(
//                postRepository::delete,
//                () -> {
//                    throw new NotFoundException("Post with ID " + seq + "not found");
//                }
//            );
//
//        // Imperative
//        var optionalPostEntity = postRepository.findById(seq);
//        var postEntity = optionalPostEntity.orElseThrow(
//            () -> new NotFoundException("Post with ID " + seq + "not found"));
//        postRepository.delete(postEntity);
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