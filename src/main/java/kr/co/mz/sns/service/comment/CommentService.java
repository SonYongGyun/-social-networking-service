package kr.co.mz.sns.service.comment;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.entity.comment.CommentEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.comment.CommentLikeRepository;
import kr.co.mz.sns.repository.comment.CommentRepository;
import kr.co.mz.sns.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    //  private final PostService postService;
    private final ModelMapper modelMapper;

    private final CommentLikeService commentLikeService;
    @Transactional
    public void deletePostComments(Long postSeq) {
        commentRepository.deleteAll(commentRepository.findAllByPostSeq(postSeq)
                .orElseThrow( () -> new NotFoundException("It is not exist comment"))
        );
    }

    public List<CommentDto> finaAll(Long postSeq) {
        return commentRepository.findAllByPostSeq(postSeq)
                .orElseThrow(() -> new NotFoundException("It is not exist comment"))
                .stream()
                .map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class))
                .toList();
    }

    @Transactional
    public CommentDto insert(CommentDto commentDto) {
//        if (commentDto.isLike()) {
//            postService.like(commentDto.getPostSeq());
//        }

        CommentEntity commentEntity = commentRepository.save(modelMapper.map(commentDto, CommentEntity.class));
        return modelMapper.map(commentEntity, CommentDto.class);
    }

  @Transactional
  public void deleteComment(Long commentSeq) {
    Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
    var commentEntity = optional.orElseThrow(() -> new NotFoundException("It is not exist comment"));

    commentRepository.delete(commentEntity);
  }

    @Transactional
    public void update(Long commentSeq, CommentDto commentDto) {
        var optionalComment = commentRepository.findBySeq(commentSeq);
        var commentEntity = optionalComment.orElseThrow(() -> new NotFoundException("It is not exist comment"));

//        if (commentDto.isLike()) {
//            postService.like(commentDto.getPostSeq());
//        }

        commentEntity.setContent(commentDto.getContent());
    }

    @Transactional
    public CommentLikeDto like(CommentLikeDto commentLikeDto) {
        var commentEntityOptional = commentRepository.findBySeq(commentLikeDto.getCommentSeq());
        var commentEntity = commentEntityOptional.orElseThrow(() -> new NotFoundException("It is not exist comment"));
        commentEntity.setCommentLike(commentEntity.getCommentLike() + 1);
        commentRepository.save(commentEntity);

        return commentLikeService.insert(commentLikeDto);
    }
}
