package kr.co.mz.sns.controller.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.service.CommentService;
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

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(@NotNull @PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity
            .ok("Success!");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> update(@NotNull @PathVariable("commentId") Long commentId,
        @Valid @RequestBody CommentDto commentDto) {
        commentService.update(commentId, commentDto);
        return ResponseEntity
            .ok("Success!");
    }
}
