package kr.co.mz.sns.service.comment;

import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentFileDto;
import kr.co.mz.sns.entity.comment.CommentFileEntity;
import kr.co.mz.sns.file.FileStorageService;
import kr.co.mz.sns.repository.comment.CommentFileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommentFileService {

    private final CommentFileRepository commentFileRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public List<CommentFileDto> insert(CommentDto insertedCommentDto, List<MultipartFile> multipartFiles) {
        return fileStorageService.convertTo(multipartFiles, CommentFileDto::from)
                .stream()
                .map(commentFile -> {
                    commentFile.setCommentSeq(insertedCommentDto.getSeq());
                    commentFile.setPostSeq(insertedCommentDto.getPostSeq());
                    return commentFile;
                })
                .map(commentFile ->
                        commentFileRepository.save(modelMapper.map(commentFile, CommentFileEntity.class))
                )
                .map(entity -> modelMapper.map(entity, CommentFileDto.class)
                ).toList();
    }

    @Transactional
    public void delete(Long commentSeq) {
        commentFileRepository.deleteByCommentSeq(commentSeq);
    }
}
