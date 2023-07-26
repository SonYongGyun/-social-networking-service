package kr.co.mz.sns.controllers;

import jakarta.validation.Valid;
import kr.co.mz.sns.dto.FindPostDto;
import kr.co.mz.sns.service.PostService;
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
@RequestMapping("/api/auth/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<FindPostDto> write(@Valid @RequestBody FindPostDto findPostDto) {
        var createdPost = postService.insert(findPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<String> delete(@PathVariable Long seq) {
        postService.deleteBySeq(seq);
        return ResponseEntity.ok("Post with ID " + seq + " has been successfully deleted");
    }

    @PutMapping("/{seq}")
    public ResponseEntity<FindPostDto> update(@PathVariable Long seq, @Valid @RequestBody FindPostDto findPostDto) {
        return ResponseEntity.ok(
            postService.updateBySeq(seq, findPostDto)
        );
    }
}
