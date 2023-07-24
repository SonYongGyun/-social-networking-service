package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.UserDetailDto;
import kr.co.mz.sns.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/user_detail/")
public class UserDetailController {

  private UserDetailService userDetailService;

  public UserDetailController(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @RequestMapping("")
  public ResponseEntity<UserDetailDto> update(UserDetailDto userDetailDto) {

    var updateDetail = userDetailService.saveOne(userDetailDto);
//    var updatedDto = userDetailService.
    return ResponseEntity.status(HttpStatus.OK).body(updateDetail);
  }
}
