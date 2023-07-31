package kr.co.mz.sns.controller.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.PostLikeDto;
import kr.co.mz.sns.service.post.PostLikeService;
import kr.co.mz.sns.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/posts")
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<GenericPostDto> insert(
        @RequestPart("files") List<MultipartFile> files,
        @Valid @RequestPart GenericPostDto postDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                postService.insert(files, postDto)
            );
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<GenericPostDto> delete(@NotNull @PathVariable Long seq) {
        return ResponseEntity.ok(
            postService.deleteByKey(seq)
        );
    }

    @PutMapping("/{seq}")
    public ResponseEntity<GenericPostDto> update(@NotNull @PathVariable Long seq,
        @Valid @RequestBody GenericPostDto genericPostDto) {
        genericPostDto.setSeq(seq);
        var updatedPostDto = postService.updateByKey(genericPostDto);
        return ResponseEntity.ok(updatedPostDto);
    }

    @PostMapping("/{seq}/like")
    public ResponseEntity<List<PostLikeDto>> like(@NotNull @PathVariable Long seq) {
        var insertedPostLikeDto = postService.like(seq);
        log.debug("PostLike inserted:: {}", insertedPostLikeDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                postLikeService.findAll(seq)
            );
    }

}
