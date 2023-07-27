package kr.co.mz.sns.controller.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/comments")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<String> insert(@Valid @RequestBody CommentDto commentDto) {
    commentService.insert(commentDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("Success!");
  }

    @DeleteMapping("/{commentSeq}")
    public ResponseEntity<String> delete(@NotNull @PathVariable("commentSeq") Long commentSeq) {
        commentService.deleteComment(commentSeq);
        return ResponseEntity
                .ok("Success!");
    }

  @PutMapping("/{commentSeq}")
  public ResponseEntity<String> update(@NotNull @PathVariable("commentSeq") Long commentSeq,
      @Valid @RequestBody CommentDto commentDto) {
    commentService.update(commentSeq, commentDto);
    return ResponseEntity
        .ok("Success!");
  }

    @PutMapping("/{commentSeq}/like") // 같은 PutMapping
    public ResponseEntity<CommentLikeDto> like(@NotNull @PathVariable("commentSeq") Long commentSeq, @Valid @RequestBody CommentLikeDto commentLikeDto) {
        commentLikeDto.setCommentSeq(commentSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.like(commentLikeDto));

    }
}
