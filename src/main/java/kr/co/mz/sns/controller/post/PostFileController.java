package kr.co.mz.sns.controller.post;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.service.post.PostFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/{seq}/file")
    public ResponseEntity<GenericPostFileDto> insert(
        @RequestPart("files") List<MultipartFile> files,
        @NotNull @PathVariable Long seq
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                postFileService.insert(files, new GenericPostDto(seq))
            );
    }

    @DeleteMapping("/{seq}/file")
    public ResponseEntity<GenericPostFileDto> delete(
        @NotNull @PathVariable Long seq,
        @RequestBody GenericPostFileDto postFileDto
    ) {
        return ResponseEntity.ok(
            postFileService.delete(new GenericPostDto(seq), postFileDto)
        );
    }

}
