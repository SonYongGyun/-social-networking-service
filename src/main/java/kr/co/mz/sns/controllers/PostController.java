package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.exception.InvalidPathVariableFormatException;
import kr.co.mz.sns.service.PostService;
import kr.co.mz.sns.valid.RequestParamValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/posts/")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<PostDto> write(@RequestBody PostDto postDto) {
        RequestParamValidation.validate(postDto);
        var createdPost = postService.saveOne(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("{seq}")
    public ResponseEntity<String> delete(@PathVariable Long seq) {
        try {
            postService.deleteBySeq(seq);
            return ResponseEntity.ok("Post with ID " + seq + " has been successfully deleted");
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new InvalidPathVariableFormatException("Invalid ID format : " + seq + " : " + nfe.getMessage());
        }
    }

    @PatchMapping("{seq}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long seq, @RequestBody PostDto postDto) {
        try {
            RequestParamValidation.validate(postDto);
            var updatedPost = postService.updateBySeq(seq, postDto);
            return ResponseEntity.ok(updatedPost);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new InvalidPathVariableFormatException("Invalid ID format : " + seq + " : " + nfe.getMessage());
        }
    }
}
