package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
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
  public ResponseEntity<Set<CompleteUserProfileDto>> get() {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(
            userProfileService.findAll(currentUserInfo.getSeq())
        );
  }

  //todo update prifile

  @PostMapping
  public ResponseEntity<List<CompleteUserProfileDto>> upload(
      @Valid @RequestParam("files") List<MultipartFile> files
  ) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
            userProfileService.insert(files)
        );
  }

  @DeleteMapping("/{profileSeq}")
  public ResponseEntity<CompleteUserProfileDto> delete(@Valid @PathVariable Long profileSeq) {
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(
            userProfileService.delete(profileSeq)
        );
  }

  @DeleteMapping
  public ResponseEntity<List<Long>> deleteAll() {
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(
            userProfileService.deleteAll(currentUserInfo.getSeq())
        );
  }

}
