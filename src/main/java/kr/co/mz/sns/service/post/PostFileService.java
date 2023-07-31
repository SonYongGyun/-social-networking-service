package kr.co.mz.sns.service.post;

import java.io.File;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.entity.post.PostFileEntity;
import kr.co.mz.sns.file.FileStorageService;
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

    public List<GenericPostFileDto> findAllByPostSeq(Long postSeq) {
        return postFileRepository.findAllByPostSeq(postSeq).stream()
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
            .toList();
    }

    @Transactional
    public List<GenericPostFileDto> updateAllByPostSeq(List<GenericPostFileDto> postFiles, Long postSeq) {
        deleteAllByPostSeq(postSeq);
        return insert(postFiles, postSeq);
    }

    @Transactional
    public List<GenericPostFileDto> insert(List<MultipartFile> multipartFiles, Long postSeq) {
        return FileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
            .stream()
            .map(postFile -> {
                postFile.setPostSeq(postSeq);
                return postFile;
            })
            .map(postFile ->
                postFileRepository.save(modelMapper.map(postFile, PostFileEntity.class))
            )
            .map(entity -> modelMapper.map(entity, GenericPostFileDto.class)
            ).toList();
    }

    @Transactional
    public List<GenericPostFileDto> deleteAllByPostSeq(Long postSeq) {
        var deletedPostFiles = postFileRepository.findAllByPostSeq(postSeq).stream().map(
            entity -> {
                var filePath = entity.getPath() + File.separator + entity.getUuid() + "." + entity.getExtension();
                FileStorageService.delete(filePath);
                return modelMapper.map(entity, GenericPostFileDto.class);
            }
        ).toList();
        postFileRepository.deleteAllByPostSeq(postSeq);
        return deletedPostFiles;
    }
}
