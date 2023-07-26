package kr.co.mz.sns.service;

import java.util.List;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.entity.PostLikeEntity;
import kr.co.mz.sns.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
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
