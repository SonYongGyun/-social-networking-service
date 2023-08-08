package kr.co.mz.sns.service.post;

import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.file.GenericPostFileDto;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<GenericPostFileDto> findAllByPostSeqs(List<Long> postSeqs) {
        return postFileRepository.findAllByPostSeqs(postSeqs);
    }

    @Transactional
    public List<GenericPostFileDto> insertAll(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        var fileList = fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
                .stream()
                .map(postFile -> {
                    var postFileEntity = modelMapper.map(postFile, PostFileEntity.class);
                    postFileEntity.setPostSeq(genericPostDto.getSeq());
                    return postFileEntity;
                })
                .toList();
        return postFileRepository.saveAll(fileList).stream()
                .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
                .toList();
    }

    @Transactional
    public GenericPostFileDto insert(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        var insertedPostFileDto = fileStorageService.convertTo(multipartFiles, GenericPostFileDto::from)
                .stream()
                .map(postFile -> {
                    var postFileEntity = modelMapper.map(postFile, PostFileEntity.class);
                    postFileEntity.setPostSeq(genericPostDto.getSeq());
                    return postFileRepository.save(postFileEntity);
                })
                .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
                .findFirst().orElseThrow();
        fileStorageService.saveFile(multipartFiles, genericPostDto);

        return insertedPostFileDto;
    }

    @Transactional
    public GenericPostFileDto delete(GenericPostDto genericPostDto, GenericPostFileDto genericPostFileDto) {
        return postFileRepository.findByPostSeqAndName(
                        genericPostDto.getSeq(),
                        genericPostFileDto.getName()
                ).map(entity -> {
                    var fileDto = modelMapper.map(entity, GenericPostFileDto.class);
                    postFileRepository.delete(entity);
                    return fileDto;
                })
                .orElseThrow(() -> new NotFoundException("File does not exist"));
    }

    @Transactional
    public Set<GenericPostFileDto> deleteAllByPostSeq(Long postSeq) {
        var deletedPostFiles = postFileRepository.findAllByPostSeq(postSeq).stream()
                .map(entity -> modelMapper.map(entity, GenericPostFileDto.class))
                .collect(Collectors.toSet());
        if (!deletedPostFiles.isEmpty()) {
            postFileRepository.deleteAllByPostSeq(postSeq);
        }
        System.out.println(deletedPostFiles);

        return deletedPostFiles;
    }
}
