package kr.co.mz.sns.controller.comment;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.dto.comment.CommentLikeDto;
import kr.co.mz.sns.dto.comment.CommentMentionDto;
import kr.co.mz.sns.service.comment.CommentLikeService;
import kr.co.mz.sns.service.comment.CommentMentionService;
import kr.co.mz.sns.service.comment.CommentService;
import kr.co.mz.sns.service.user.UserService;
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
  private final CommentMentionService commentMentionService;
  private final UserService userService;

  @PostMapping
  public ResponseEntity<String> insert(@Valid @RequestBody CommentDto commentDto) {
    var commentMomentDto_ = commentMentionService.split(commentDto);

//    userService.mention(userNameList); //mention없으면 안되게
//
//    commentService.insert(commentDto_);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("The comment has been successfully registered!");
  }

  @DeleteMapping("/{commentSeq}")
  public ResponseEntity<String> delete(@NotNull @PathVariable("commentSeq") Long commentSeq) {
    commentService.deleteComment(commentSeq);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body("The comment has been successfully deleted!");
  }

  @PutMapping("/{commentSeq}")
  public ResponseEntity<String> update(@NotNull @PathVariable("commentSeq") Long commentSeq,
                                       @Valid @RequestBody CommentDto commentDto) {
    commentDto.setSeq(commentSeq);
    commentService.update(commentDto);
    return ResponseEntity
            .ok("The content has been successfully updated!");
  }

  @PutMapping("/{commentSeq}/like")
  public ResponseEntity<List<CommentLikeDto>> like(@NotNull @PathVariable("commentSeq") Long commentSeq, @Valid @RequestBody CommentLikeDto commentLikeDto) {
    commentLikeDto.setCommentSeq(commentSeq);
    return ResponseEntity.status(HttpStatus.CREATED).body(commentService.like(commentLikeDto));
  }
}
