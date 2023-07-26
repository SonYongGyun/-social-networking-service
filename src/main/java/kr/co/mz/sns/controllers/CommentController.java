package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody CommentDto commentDto) {
        commentService.insert(commentDto);
        return ResponseEntity.ok("Success!");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok("Success!");
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<String> update(@PathVariable("commentId") Long commentId,
        @RequestBody CommentDto commentDto) {
        commentService.update(commentId, commentDto);
        return ResponseEntity.ok("Success!");
    }
}
