package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import kr.co.mz.sns.dto.user.FindUserDetailDto;
import kr.co.mz.sns.dto.user.GenericUserProfileDto;
import kr.co.mz.sns.dto.user.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.UpdateUserDetailDto;
import kr.co.mz.sns.service.user.UserDetailService;
import kr.co.mz.sns.service.user.UserProfileService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users")
public class UserDetailController {

  private final UserDetailService userDetailService;
  private final UserProfileService userProfileService;
  private final CurrentUserInfo currentUserInfo;

  @GetMapping
  public ResponseEntity<FindUserDetailDto> findByEmailWithProfile() {
    return ResponseEntity
        .ok(
            userDetailService.findByEmail(
                currentUserInfo.getEmail()
            )
        );
  }


  @PostMapping
  public ResponseEntity<InsertUserDetailDto> insertInfo(@RequestBody InsertUserDetailDto insertUserDetailDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
            userDetailService.insert(insertUserDetailDto)

        );
  }

  @GetMapping("/profiles")
  public ResponseEntity<Set<GenericUserProfileDto>> getProfile() {

    return ResponseEntity.status(HttpStatus.OK)
        .body(userProfileService.findAll(currentUserInfo.getSeq())); //todo get, update prifile
  }

  @PostMapping("/upload")
  public ResponseEntity<List<GenericUserProfileDto>> uploadProfile(
      @Valid @RequestParam List<MultipartFile> files
  ) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userProfileService.insert(files));
  }

  @DeleteMapping("/{profileSeq}")
  public ResponseEntity<String> deleteProfile(@Valid @PathVariable Long profileSeq) {
    return ResponseEntity.ok(
        "Your detail is deleted Successfully for :" + userProfileService.delete(profileSeq) + " rows."
    );
  }

//  @GetMapping("/download")
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

  @PutMapping
  public ResponseEntity<InsertUserDetailDto> updateInfo(@RequestBody UpdateUserDetailDto
      updateUserDetailDto) {//업데이트할때는 seq 넣어서 보내주는걸로
    return ResponseEntity
        .ok(
            userDetailService.updateByUserSeq(
                updateUserDetailDto
            )
        );
  }

  @DeleteMapping("/{userSeq}")
  public ResponseEntity<String> deleteInfo(@NotNull @PathVariable Long userSeq) {
    return ResponseEntity
        .ok(
            "Your detail is deleted Successfully for :" + userDetailService.deleteByUserSeq(userSeq) + " rows."
        );
  }
}
