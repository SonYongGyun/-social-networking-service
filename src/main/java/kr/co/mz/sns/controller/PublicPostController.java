package kr.co.mz.sns.controller;

import java.util.List;
import kr.co.mz.sns.dto.post.FindPostDto;
import kr.co.mz.sns.dto.post.PostSearchDto;
import kr.co.mz.sns.exception.InvalidPathVariableFormatException;
import kr.co.mz.sns.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicPostController {

  private final PostService postService;

  @Autowired
  public PublicPostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/posts")
  public ResponseEntity<List<FindPostDto>> getAll(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, PostSearchDto postSearchDto) {
    List<FindPostDto> posts;
    var pageable = PageRequest.of(page, size);
    if (postSearchDto == null) {
      posts = postService.findAll(pageable);
    } else {
      posts = postService.findByKeyword(postSearchDto, pageable);
    }
    return ResponseEntity.ok(posts);
  }

  @GetMapping("/posts/{seq}")
  public ResponseEntity<FindPostDto> getById(@PathVariable Long seq) {
    try {
      var post = postService.findByKey(seq);
      return ResponseEntity.ok(post);
    } catch (NumberFormatException | NullPointerException nfe) {
      throw new InvalidPathVariableFormatException("Invalid ID format : " + seq + " : " + nfe.getMessage());
    }
  }
}
