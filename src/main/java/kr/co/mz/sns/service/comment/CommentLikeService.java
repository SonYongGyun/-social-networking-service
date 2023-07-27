package kr.co.mz.sns.service.comment;

import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.entity.comment.CommentLikeEntity;
import kr.co.mz.sns.entity.post.PostLikeEntity;
import kr.co.mz.sns.repository.comment.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommentLikeService {

    private final ModelMapper modelMapper;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentLikeDto insert(CommentLikeDto commentLikeDto){
        var commentLikeEntity = new CommentLikeEntity();
        commentLikeEntity.setCommentSeq(commentLikeDto.getCommentSeq());
        commentLikeEntity.setPostSeq(commentLikeDto.getPostSeq());

        return modelMapper.map(
                 commentLikeRepository.save(commentLikeEntity),
                CommentLikeDto.class);

    }
}
