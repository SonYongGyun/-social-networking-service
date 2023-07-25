package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.UserDetailDto;
import kr.co.mz.sns.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/user_detail/")
public class UserDetailController {

  private final UserDetailService userDetailService;

  public UserDetailController(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @GetMapping("{userSeq}")
  public ResponseEntity<UserDetailDto> findByUserSeq(@PathVariable Long userSeq) {
    var optionalUserDetailDto = userDetailService.findById(userSeq);

    return ResponseEntity.status(HttpStatus.OK).body(optionalUserDetailDto);
  }

  @PostMapping("")
  public ResponseEntity<UserDetailDto> writeDetail(@RequestBody UserDetailDto userDetailDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userDetailService.saveOne(userDetailDto));
  }

  @PatchMapping("")
  public ResponseEntity<UserDetailDto> update(@RequestBody UserDetailDto
      userDetailDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userDetailService.saveOne(userDetailDto));
  }

  @DeleteMapping("{userSeq}")
  public ResponseEntity<String> delete(@PathVariable Long userSeq) {
    var result = userDetailService.deleteByUserSeq(userSeq);
    return ResponseEntity.status(HttpStatus.OK).body("Your detail is deleted Successfully for :" + result + " rows.");
  }
}
