package kr.co.mz.sns.service.post;

import java.io.File;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
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
        return postFileRepository.findAllByPostSeq(postSeq).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .toList();
    }

    @Transactional
    public List<GenericPostFileDto> insertAll(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        var fileList = fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
            .stream()
            .map(postFile -> {
                postFile.setPostSeq(genericPostDto.getSeq());
                return modelMapper.map(postFile, PostFileEntity.class);
            }).toList();
        return postFileRepository.saveAll(fileList).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class)
            ).toList();
    }

    @Transactional
    public GenericPostFileDto insert(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        return fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
            .stream()
            .map(postFile -> {
                postFile.setPostSeq(genericPostDto.getSeq());
                return postFile;
            })
            .map(postFile -> postFileRepository.save(modelMapper.map(postFile, PostFileEntity.class)))
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .findFirst().orElseThrow();
    }

    @Transactional
    public GenericPostFileDto delete(GenericPostDto genericPostDto, GenericPostFileDto genericPostFileDto) {
        var findPostFileEntity = postFileRepository.findByPostSeqAndName(genericPostDto.getSeq(),
                genericPostFileDto.getName())
            .orElseThrow(() -> new NotFoundException("File does not exist"));
        postFileRepository.delete(findPostFileEntity);
        return modelMapper.map(findPostFileEntity, GenericPostFileDto.class);
    }

    @Transactional
    public List<GenericPostFileDto> deleteAllByPostSeq(Long postSeq) {
        var deletedPostFiles = postFileRepository.findAllByPostSeq(postSeq).stream().map(
            entity -> {
                var filePath =
                    entity.getPath() + File.separator + entity.getSeq() + "_" + entity.getName()
                        + "_" + entity.getSeq() + "." + entity.getExtension();
                fileStorageService.delete(filePath);
                return modelMapper.map(entity, GenericPostFileDto.class);
            }
        ).toList();
        postFileRepository.deleteAllByPostSeq(postSeq);
        return deletedPostFiles;
    }
}
