package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.service.PostService;
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
@RequestMapping("/api/auth")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/posts")
    public ResponseEntity<PostDto> write(@RequestBody PostDto postDto) {
        var createdPost = postService.saveOne(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        postService.deleteById(id);
        return ResponseEntity.ok("Post with ID "+ id +" has been successfully deleted");
    }
    @PatchMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto){
        var updatedPost = postService.updateById(id,postDto);
        return ResponseEntity.ok(updatedPost);
    }
}
