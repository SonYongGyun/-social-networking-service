package kr.co.mz.sns.service.comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.entity.comment.CommentEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.comment.CommentRepository;
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
    private final ModelMapper modelMapper;
    private final CommentLikeService commentLikeService;

    public CommentDto findBySeq(Long seq) {
        return commentRepository.findBySeq(seq)
            .map(entity -> modelMapper.map(entity, CommentDto.class))
            .orElseThrow(() -> new NotFoundException("Comment Not Found:" + seq));
    }

    public Set<CommentDto> findAllByPostSeq(Long postSeq) {
        return commentRepository.findAllByPostSeq(postSeq)
            .stream()
            .map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class))
            .collect(Collectors.toSet());
    }

    @Transactional
    public CommentDto insert(CommentDto commentDto) {
        var commentEntity = commentRepository.save(
            modelMapper.map(commentDto, CommentEntity.class)
        );

        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Transactional
    public void deleteBySeq(Long commentSeq) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new NotFoundException("It is not exist comment"));

        commentRepository.delete(commentEntity);

        //
//        commentRepository.findBySeq(commentSeq)
//                .ifPresentOrElse(
//                        commentRepository::delete,
//                        () -> { throw new NotFoundException("It is not exist comment");}
//                );

    }

    @Transactional
    public void update(CommentDto commentDto) {
        var optionalComment = commentRepository.findBySeq(commentDto.getSeq());
        var commentEntity = optionalComment.orElseThrow(() -> new NotFoundException("It is not exist comment"));
        commentEntity.setContent(commentDto.getContent());
    }

    @Transactional
    public List<CommentLikeDto> like(Long seq) {
        return commentRepository.findBySeq(seq)
            .map(CommentEntity::increaseCommentLike)
            .map(commentEntity -> new CommentLikeDto(seq, commentEntity.getPostSeq()))
            .map(commentLikeService::insert)
            .map(commentLikeDto -> commentLikeService.findAll(seq))
            .orElseThrow(() -> new NotFoundException("Comment Not Found: " + seq));

//        var commentEntityOptional = commentRepository.findBySeq(seq);
//        var commentEntity = commentEntityOptional.orElseThrow(() -> new NotFoundException("It is not exist comment"));
//        commentEntity.increaseCommentLike();
//        commentLikeService.insert(new CommentLikeDto(seq, commentEntity.getPostSeq()));
//        return commentLikeService.findAll(seq);
    }

    @Transactional
    public Set<CommentDto> deleteAllByPostSeq(Long postSeq) {
//        commentRepository.deleteAllByPostSeq(postSeq);
        var commentEntities = commentRepository.findAllByPostSeq(postSeq);

        commentRepository.deleteAllByPostSeq(postSeq);
        commentRepository.deleteAllByCommentSeqs(
            commentEntities.stream().map(CommentEntity::getSeq).toList()
        );
        return commentEntities.stream()
            .map(entity -> modelMapper.map(entity, CommentDto.class))
            .collect(Collectors.toSet());

        // 리턴해 ... -> DTO로 해야 하는데...
//        return commentEntities.map(entity -> modelMapper.map(entity, CommentDto.class)).stream().toList();

//        commentRepository.deleteAll()
//        commentRepository.deleteAll(commentRepository.findAllByPostSeq(postSeq)
//                .orElseThrow(() -> new NotFoundException("It is not exist comment"))
//        );
    }
}
