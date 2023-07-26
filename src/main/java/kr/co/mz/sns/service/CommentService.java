package kr.co.mz.sns.service;

import java.util.Optional;
import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.exception.CommentNotFoundException;
import kr.co.mz.sns.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;

//    public List<CommentDto> viewAll(Long postSeq) {
//        Optional<List<CommentEntity>> optional = commentRepository.findByPostEntityPostSeq(postSeq);
//        var commentEntityList = optional.orElseThrow(() -> new CommentNotFoundException("It is not existed comment."));
//        return commentEntityList.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList();
//    }

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
        var commentEntity = optional.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));

        commentRepository.delete(commentEntity);
    }

    @Transactional
    public void update(Long commentSeq, CommentDto commentDto) {
        var optionalComment = commentRepository.findBySeq(commentSeq);
        var commentEntity = optionalComment.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));

        if (commentDto.isLike()) {
            postService.like(commentDto.getPostSeq());
        }

        commentEntity.setContent(commentDto.getContent());
    }

}
