package kr.co.mz.sns.service.post;

import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.dto.post.SelectPostDto;
import kr.co.mz.sns.entity.post.PostEntity;
import kr.co.mz.sns.entity.post.PostFileEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.file.FileStorageService;
import kr.co.mz.sns.mapper.ModelMapperService;
import kr.co.mz.sns.repository.post.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    private final ModelMapperService modelMapperService;

    public List<GenericPostFileDto> findAllByPostSeq(Long postSeq) {
        return postFileRepository.findAllByPostEntity_Seq(postSeq).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .toList();
    }

    @Transactional
    public List<GenericPostFileDto> insertAll(List<MultipartFile> multipartFiles, SelectPostDto selectPostDto) {
        var fileList = fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
            .stream()
            .map(postFile -> {
                var postFileEntity = modelMapper.map(postFile, PostFileEntity.class);
                postFileEntity.setPostEntity(
                    PostEntity.builder().seq(selectPostDto.getSeq()).postFiles(new ArrayList<>())
                        .build());
                return postFileEntity;
            })
            .toList();
        return postFileRepository.saveAll(fileList).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .toList();
    }

    @Transactional
    public GenericPostFileDto insert(List<MultipartFile> multipartFiles, SelectPostDto selectPostDto) {
        var insertedPostFileDto = fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
            .stream()
            .map(postFile -> {
                var postFileEntity = modelMapper.map(postFile, PostFileEntity.class);
                postFileEntity.setPostEntity(PostEntity.builder().seq(selectPostDto.getSeq()).build());
                return postFileRepository.save(postFileEntity);
            })
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .findFirst().orElseThrow();
        fileStorageService.saveFile(multipartFiles, selectPostDto);

        return insertedPostFileDto;
    }

    @Transactional
    public GenericPostFileDto delete(SelectPostDto selectPostDto, GenericPostFileDto genericPostFileDto) {
        return postFileRepository.findByPostSeqAndName(
                selectPostDto.getSeq(),
                genericPostFileDto.getName()
            ).map(entity -> {
                var fileDto = modelMapper.map(entity, GenericPostFileDto.class);
                postFileRepository.delete(entity);
                return fileDto;
            })
            .orElseThrow(() -> new NotFoundException("File does not exist"));
    }

    @Transactional
    public List<GenericPostFileDto> deleteAllByPostSeq(Long postSeq) {
        var deletedPostFiles = postFileRepository.findAllByPostEntity_Seq(postSeq).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .toList();
        if (!deletedPostFiles.isEmpty()) {
            postFileRepository.deleteAllByPostSeq(postSeq);
        }
        System.out.println(deletedPostFiles);

        return deletedPostFiles;
    }
}
