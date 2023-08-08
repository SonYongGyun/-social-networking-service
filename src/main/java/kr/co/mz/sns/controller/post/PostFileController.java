package kr.co.mz.sns.controller.post;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.file.GenericPostFileDto;
import kr.co.mz.sns.service.post.PostFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/posts")
public class PostFileController {

    private final PostFileService postFileService;

    @GetMapping("/{seq}/file/{fileSeq}")
    public ResponseEntity<String> select(
            @NotNull @PathVariable Long seq,
            @NotNull @PathVariable Long fileSeq
    ) {
        return null;
    }

    @PostMapping("/{seq}/file")
    public ResponseEntity<GenericPostFileDto> insert(
            @RequestPart("files") List<MultipartFile> files,
            @NotNull @PathVariable Long seq
    ) {
        var insertedPostFileDto = postFileService.insert(files, GenericPostDto.builder().seq(seq).build());
        return ResponseEntity.
                created(URI.create("/api/auth/posts/" + seq + "/file/" + insertedPostFileDto.getSeq()))
                .body(insertedPostFileDto);
    }

    @DeleteMapping("/{seq}/file")
    public ResponseEntity<GenericPostFileDto> delete(
            @NotNull @PathVariable Long seq,
            @RequestBody GenericPostFileDto postFileDto
    ) {
        return ResponseEntity.ok(
                postFileService.delete(GenericPostDto.builder().seq(seq).build(), postFileDto)
        );
    }

}
