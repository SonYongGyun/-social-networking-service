package kr.co.mz.sns.controller;

import static kr.co.mz.sns.service.file.FileConstants.SALVE_LOCAL_PUBLIC_DIRECTORY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import kr.co.mz.sns.dto.post.SaveFileRequestDto;
import kr.co.mz.sns.service.file.FileStorageService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unauth")
public class LocalFileController {

  private final FileStorageService fileStorageService;
  private final String localFileDirectory = SALVE_LOCAL_PUBLIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString();

  @GetMapping("/{fileName}")
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
  public ResponseEntity<String> insertFileIntoLocal(
      @RequestBody SaveFileRequestDto saveFileRequestDto
  ) {
    var base64FilesStringList = saveFileRequestDto.getByteFileList();

    var genericPostDto = saveFileRequestDto.getGenericPostDto();
    String message = "Saved into localDirectory.";
    fileStorageService.saveFile(null, genericPostDto);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(message);
  }
}
