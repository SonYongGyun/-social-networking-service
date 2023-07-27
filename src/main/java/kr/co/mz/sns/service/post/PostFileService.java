package kr.co.mz.sns.service.post;

import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.entity.post.PostFileEntity;
import kr.co.mz.sns.repository.post.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final ModelMapper modelMapper;

//    public

    @Transactional
    public List<GenericPostFileDto> insert(List<GenericPostFileDto> postFileDtoSet) {
        return postFileDtoSet.stream()
            .map((postFile) -> postFileRepository.save(modelMapper.map(postFile, PostFileEntity.class)))
            .map((postFileEntity) -> modelMapper.map(postFileEntity, GenericPostFileDto.class))
            .toList();
    }

}
