package kr.co.mz.sns.controllers;

import java.util.List;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post/")
public class PostController {

  private final PostRepository postRepository;

  @Autowired
  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping("/hasAuth")
  public ResponseEntity<PostDto> authorizedBoard() {
    var postDto = new PostDto();
    return new ResponseEntity<>(postDto, HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<PostEntity>> postList() {
    var postList = postRepository.findAll();
    
    return new ResponseEntity<>(postList, HttpStatus.OK);
  }
}
