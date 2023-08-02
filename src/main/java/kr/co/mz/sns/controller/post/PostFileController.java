package kr.co.mz.sns.controller.post;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.dto.post.SelectPostDto;
import kr.co.mz.sns.service.post.PostFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        var insertedPostFileDto = postFileService.insert(files, new SelectPostDto(seq));
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
            postFileService.delete(new SelectPostDto(seq), postFileDto)
        );
    }

}
