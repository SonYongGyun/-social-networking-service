package kr.co.mz.sns.service.comment;

import java.util.List;
import java.util.stream.Collectors;

import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.entity.comment.CommentLikeEntity;
import kr.co.mz.sns.entity.post.PostLikeEntity;
import kr.co.mz.sns.exception.NotFoundException;
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
    public CommentLikeDto insert(CommentLikeDto commentLikeDto) { //코드 간추리기
        var commentLikeEntity = new CommentLikeEntity(commentLikeDto.getCommentSeq(),commentLikeDto.getPostSeq());

        return modelMapper.map(
                commentLikeRepository.save(commentLikeEntity),
                CommentLikeDto.class);
    }

    @Transactional
    public List<CommentLikeDto> findAll(Long commentSeq) {  //orelse
        return commentLikeRepository.findByCommentSeq(commentSeq).stream()
                .map(entity -> modelMapper.map(entity, CommentLikeDto.class))
                .toList();
    }
}
