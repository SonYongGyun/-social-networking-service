package kr.co.mz.sns.service;

import java.util.Optional;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.CommentLikeRepository;
import kr.co.mz.sns.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;


    @Transactional
    public CommentDto insert(CommentDto commentDto) {
        if (commentDto.isLike()) {
            postService.like(commentDto.getPostSeq());
        }

        CommentEntity commentEntity = commentRepository.save(modelMapper.map(commentDto, CommentEntity.class));
        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Transactional
    public void delete(Long commentSeq) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new NotFoundException("It is not exist comment"));

        commentRepository.delete(commentEntity);
    }

    @Transactional
    public void update(Long commentSeq, CommentDto commentDto) {
        var optionalComment = commentRepository.findBySeq(commentSeq);
        var commentEntity = optionalComment.orElseThrow(() -> new NotFoundException("It is not exist comment"));

        if (commentDto.isLike()) {
            postService.like(commentDto.getPostSeq());
        }

        commentEntity.setContent(commentDto.getContent());
    }

//    public CommentLikeDto like(Long commentSeq){
//        var commentEntityOptional = commentRepository.findBySeq(commentSeq);
//        var commentEntity = commentEntityOptional.orElseThrow(() -> new NotFoundException("It is not exist comment"));
//        commentEntity.setCommentLike(commentEntity.getCommentLike() + 1);
//        commentRepository.save(commentEntity);
//
//        return commentLikeService.insert();
//    }
}
