package kr.co.mz.sns.controllers;

import java.util.List;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAll(){
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(postService.findById(id),HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<PostDto> write(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.saveOne(postDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        postService.deleteById(id);
        return ResponseEntity.ok().body("Post with ID "+ id +" has been successfully deleted");
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<PostDto> partialUpdatePost(@PathVariable Long id, @RequestBody Map<String, O>){
//
//    }
}
