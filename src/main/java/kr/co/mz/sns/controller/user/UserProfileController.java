package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import kr.co.mz.sns.dto.user.GenericUserProfileDto;
import kr.co.mz.sns.service.user.UserProfileService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users/profiles")
public class UserProfileController {

  private final UserProfileService userProfileService;
  private final CurrentUserInfo currentUserInfo;

  @GetMapping
  public ResponseEntity<Set<GenericUserProfileDto>> getProfile() {

    return ResponseEntity.status(HttpStatus.OK)
        .body(userProfileService.findAll(currentUserInfo.getSeq()));
  }

  //todo update prifile
  @DeleteMapping("/{profileSeq}")
  public ResponseEntity<String> deleteProfile(@Valid @PathVariable Long profileSeq) {//todo server local 에서 삭제?
    return ResponseEntity.ok(
        "Your detail is deleted Successfully for :" + userProfileService.delete(profileSeq) + " rows."
    );
  }

  @PostMapping
  public ResponseEntity<List<GenericUserProfileDto>> uploadProfile(
      @Valid @RequestParam List<MultipartFile> files
  ) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userProfileService.insert(files));
  }

//  @GetMapping("/profiles/download/{profileSeq}")
//  public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName) {
//    var inputStream = FileStorageService.downloadFile(
//        fileService.findByName(new GenericUserDetailFileDto(fileName))
//    );
//    var headers = new HttpHeaders();
//    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//    return ResponseEntity
//        .ok()
//        .headers(headers)
//        .contentType(MediaType.APPLICATION_OCTET_STREAM)
//        .body(new InputStreamResource(inputStream));
//  }
}
