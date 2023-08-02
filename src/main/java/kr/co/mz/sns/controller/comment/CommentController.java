package kr.co.mz.sns.controller.comment;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.service.comment.CommentFileService;
import kr.co.mz.sns.service.comment.CommentService;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final CommentFileService commentFileService;

    @PostMapping
    public ResponseEntity<CommentDto> insert(@Valid @RequestPart CommentDto commentDto,
                                             @RequestPart("files") @Nullable List<MultipartFile> files) {
//        if (files != null && !files.isEmpty()) {
//            commentService.insert(commentDto, files);
//        }
        commentDto.splitContentAndMentionedUsername();

//        userService.mention(commentDto.getMentionedUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.insert(commentDto, files));
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<String> delete(@NotNull @PathVariable("seq") Long seq) {
        commentService.deleteBySeq(seq);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{seq}")
    public ResponseEntity<CommentDto> update(@NotNull @PathVariable("seq") Long seq,
                                             @Valid @RequestPart CommentDto commentDto,
                                             @RequestPart("files") @Nullable List<MultipartFile> files) {
        commentDto.setSeq(seq);
        commentDto.splitContentAndMentionedUsername();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.update(commentDto, files));
    }

    @PutMapping("/{seq}/like")
    public ResponseEntity<List<CommentLikeDto>> like(@NotNull @PathVariable("seq") Long seq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        commentService.like(seq)
                );
    }

//    @PostMapping("/{seq}/file")
//    public ResponseEntity<List<CommentFileDto>> file(@NotNull @PathVariable("seq") Long seq){
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(
//                );
//    }
}
