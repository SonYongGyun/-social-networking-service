package kr.co.mz.sns.service.comment;

import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.entity.comment.CommentEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.file.FileStorageService;
import kr.co.mz.sns.mapper.ModelMapperService;
import kr.co.mz.sns.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final CommentLikeService commentLikeService;
    private final ModelMapperService modelMapperService;
    private final CommentFileService commentFileService;

    public CommentDto findBySeq(Long seq) {
        return commentRepository.findBySeq(seq)
                .map(entity -> modelMapper.map(entity, CommentDto.class))
                .orElseThrow(() -> new NotFoundException("Comment Not Found:" + seq));
    }

    public Set<CommentDto> findAllByPostSeq(Long postSeq) {
        return commentRepository.findAllByPostEntity_Seq(postSeq)
                .stream()
                .map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class))
                .collect(Collectors.toSet());
    }

    @Transactional
    public CommentDto insert(CommentDto commentDto, List<MultipartFile> multipartFiles) {
        var insertedCommentDto =
                modelMapperService.mapAndActAndMap(
                        Optional.of(commentDto).stream(),
                        CommentEntity.class,
                        commentRepository::save,
                        CommentDto.class
                ).findFirst().orElseThrow();
        if (multipartFiles != null) {
            var savedFiles = commentFileService.insert(insertedCommentDto, multipartFiles);
            commentDto.setCommentFiles(savedFiles);
            FileStorageService.saveCommentFile(multipartFiles, commentDto);
        }
        return commentDto;
    }

    @Transactional
    public void deleteBySeq(Long commentSeq) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new NotFoundException("It is not exist comment"));

        commentRepository.delete(commentEntity);
        commentFileService.delete(commentSeq);
    }

    @Transactional
    public CommentDto update(CommentDto commentDto, List<MultipartFile> multipartFiles) {
        var optionalComment = commentRepository.findBySeq(commentDto.getSeq());
        var commentEntity = optionalComment.orElseThrow(() -> new NotFoundException("It is not exist comment"));
        commentEntity.setContent(commentDto.getContent());
        var insertedCommentDto = modelMapper.map(commentRepository.save(commentEntity), CommentDto.class);

        commentFileService.delete(commentDto.getSeq());
        if (multipartFiles != null) {
            var savedFiles = commentFileService.insert(insertedCommentDto, multipartFiles);
            commentDto.setCommentFiles(savedFiles);
            FileStorageService.saveCommentFile(multipartFiles, commentDto);
        }
        return commentDto;
    }

    @Transactional
    public List<CommentLikeDto> like(Long seq) {
        return commentRepository.findBySeqWithPost(seq)
                .map(CommentEntity::increaseCommentLike)
                .map(commentEntity -> new CommentLikeDto(seq, commentEntity.getPostEntity().getSeq()))
                .map(commentLikeService::insert)
                .map(commentLikeDto -> commentLikeService.findAll(seq))
                .orElseThrow(() -> new NotFoundException("Comment Not Found: " + seq));
    }

    @Transactional
    public Set<CommentDto> deleteAllByPostSeq(Long postSeq) {
//        commentRepository.deleteAllByPostSeq(postSeq);
        var commentEntities = commentRepository.findAllByPostEntity_Seq(postSeq);

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
