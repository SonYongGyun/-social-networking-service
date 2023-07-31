package kr.co.mz.sns.controller.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.service.comment.CommentService;
import kr.co.mz.sns.service.user.UserDetailService;
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
  private final UserDetailService userDetailService;

  @PostMapping
  public ResponseEntity<CommentDto> insert(@Valid @RequestBody CommentDto commentDto) {
    commentDto.splitContentAndMentionedUsername();
    var insertedComment = commentService.insert(commentDto);
    userDetailService.mention(commentDto.getMentionedUsername());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(insertedComment);
  }

  @DeleteMapping("/{seq}")
  public ResponseEntity<String> delete(@NotNull @PathVariable("seq") Long seq) {
    commentService.deleteBySeq(seq);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{seq}")
  public ResponseEntity<String> update(@NotNull @PathVariable("seq") Long seq,
      @Valid @RequestBody CommentDto commentDto) {
    commentDto.setSeq(seq);
    commentDto.splitContentAndMentionedUsername();
    commentService.update(commentDto);

    return ResponseEntity
        .ok("The content has been successfully updated!");
  }

  @PutMapping("/{seq}/like")
  public ResponseEntity<List<CommentLikeDto>> like(@NotNull @PathVariable("seq") Long seq) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            commentService.like(seq)
        );
  }
}
