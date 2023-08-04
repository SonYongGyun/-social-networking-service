package kr.co.mz.sns.controller.post;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.post.FindAllPostDto;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.PostSearchDto;
import kr.co.mz.sns.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/posts")
public class PublicPostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<FindAllPostDto>> getAll(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size, @Nullable @RequestParam String keyword) {
        List<FindAllPostDto> posts;
        var pageable = PageRequest.of(page, size);
        if (keyword == null) {
            posts = postService.findAll(pageable);
        } else {
            posts = postService.findAllByKeyword(new PostSearchDto(keyword), pageable);
        }
        return ResponseEntity
            .ok(posts);
    }

    @GetMapping("/{seq}")
    public ResponseEntity<GenericPostDto> get(@NotNull @PathVariable Long seq) {
        return ResponseEntity
            .ok(
                postService.findByKey(seq)
            );
    }
}
