package kr.co.mz.sns.service.post;

import kr.co.mz.sns.dto.post.like.PostLikeDto;
import kr.co.mz.sns.entity.post.PostLikeEntity;
import kr.co.mz.sns.repository.post.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PostLikeDto insert(Long postSeq) {
        var postLikeEntity = new PostLikeEntity();
        postLikeEntity.setPostSeq(postSeq);

        return modelMapper.map(
                postLikeRepository.save(postLikeEntity),
                PostLikeDto.class
        );
    }

    public List<PostLikeDto> findAll(Long postSeq) {
        return postLikeRepository.findByPostSeq(postSeq).stream()
                .map(entity -> modelMapper.map(entity, PostLikeDto.class))
                .toList();
    }
}
