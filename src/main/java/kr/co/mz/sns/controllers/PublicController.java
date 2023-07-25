package kr.co.mz.sns.controllers;

import java.util.List;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.exception.InvalidPathVariableFormatException;
import kr.co.mz.sns.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/")
public class PublicController {

    private final PostService postService;

    @Autowired
    public PublicController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostDto>> getAll() {
        var posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("posts/{seq}")
    public ResponseEntity<PostDto> getById(@PathVariable Long seq) {
        try {
            var post = postService.findBySeq(seq);
            return ResponseEntity.ok(post);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new InvalidPathVariableFormatException("Invalid ID format : " + seq + " : " + nfe.getMessage());
        }
    }
}
