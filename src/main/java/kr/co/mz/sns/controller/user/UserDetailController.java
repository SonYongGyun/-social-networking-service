package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import kr.co.mz.sns.dto.user.UserDetailDto;
import kr.co.mz.sns.dto.user.UserDetailUpdateDto;
import kr.co.mz.sns.dto.user.UserFindByEmailDto;
import kr.co.mz.sns.service.UserDetailService;
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
@RequestMapping("/api/public/user_detail")
@RequiredArgsConstructor
public class UserDetailController {

  private final UserDetailService userDetailService;


  @GetMapping
  public ResponseEntity<UserDetailDto> findByEmail(@Valid @RequestBody UserFindByEmailDto userFindByEmailDto) {
    var optionalUserDetailDto = userDetailService.findByEmail(userFindByEmailDto.getEmail());
    return ResponseEntity.status(HttpStatus.OK).body(optionalUserDetailDto);
  }

  @PostMapping
  public ResponseEntity<UserDetailDto> write(@RequestBody UserDetailDto userDetailDto) {

    return ResponseEntity.status(HttpStatus.OK).body(userDetailService.saveOne(userDetailDto));
  }

  @PutMapping
  public ResponseEntity<UserDetailDto> update(@RequestBody UserDetailUpdateDto
      userDetailUpdateDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userDetailService.updateByUserSeq(userDetailUpdateDto));
  }

  @DeleteMapping("/{userSeq}")//todo client에서 userseq 를 줄수있는 방법이 없습니다.
  public ResponseEntity<String> delete(@PathVariable Long userSeq) {
    var result = userDetailService.deleteByUserSeq(userSeq);
    return ResponseEntity.status(HttpStatus.OK).body("Your detail is deleted Successfully for :" + result + " rows.");
  }
}
