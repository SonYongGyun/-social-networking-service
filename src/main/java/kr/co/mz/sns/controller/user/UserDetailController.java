package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.detail.CompleteUserDetailDto;
import kr.co.mz.sns.dto.user.detail.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.detail.UpdateUserDetailDto;
import kr.co.mz.sns.dto.user.detail.UserDetailAndProfileDto;
import kr.co.mz.sns.service.user.UserDetailService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users")
public class UserDetailController {

  private final UserDetailService userDetailService;
  private final CurrentUserInfo currentUserInfo;

  @GetMapping
  public ResponseEntity<UserDetailAndProfileDto> findByEmailWithProfile() {
    return ResponseEntity
        .ok(
            userDetailService.findByEmail(
                currentUserInfo.getEmail()
            )
        );
  }


  @PostMapping
  public ResponseEntity<CompleteUserDetailDto> insert(@RequestBody InsertUserDetailDto insertUserDetailDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
            userDetailService.insert(insertUserDetailDto)
        );
  }


  @PutMapping
  public ResponseEntity<CompleteUserDetailDto> update(@RequestBody UpdateUserDetailDto
      updateUserDetailDto) {//업데이트할때는 seq 넣어서 보내주는걸로
    return ResponseEntity
        .ok(
            userDetailService.updateByUserSeq(
                updateUserDetailDto
            )
        );
  }

  @DeleteMapping("/{userSeq}")
  public ResponseEntity<CompleteUserDetailDto> delete(@NotNull @PathVariable Long userSeq) {
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(
            userDetailService.deleteByUserSeq(userSeq)
        );
  }
}
