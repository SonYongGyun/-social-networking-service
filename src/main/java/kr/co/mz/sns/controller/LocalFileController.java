package kr.co.mz.sns.controller;

import static kr.co.mz.sns.service.file.FileConstants.SALVE_LOCAL_PUBLIC_DIRECTORY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.dto.LocalFileResponseDto;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unauth/files")
public class LocalFileController {

    private final FileStorageService fileStorageService;
    private final String localFileDirectory =
        SALVE_LOCAL_PUBLIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString();

    @GetMapping
    public ResponseEntity<byte[]> getPublicImage(@PathVariable String fileName) {
        try (
            var imageStream = new FileInputStream(new File(localFileDirectory, fileName))
        ) {
            var imageBytes = imageStream.readAllBytes();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException ioe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<LocalFileResponseDto> insertFileIntoLocal(
        @RequestBody List<MultipartFile> multipartFilesList,
        @RequestBody GenericPostDto genericPostDto
    ) {
        var fileResponses = new ArrayList<LocalFileResponseDto>();

        String message = "Saved into localDirectory.";
        fileStorageService.saveFile(multipartFilesList, genericPostDto);
        return ResponseEntity.ok(new LocalFileResponseDto("filepath", message));
    }
}
