package kr.co.mz.sns.controller.login;

import jakarta.validation.Valid;
import java.net.URI;
import kr.co.mz.sns.dto.login.LoginDto;
import kr.co.mz.sns.dto.login.RegisterUserDto;
import kr.co.mz.sns.service.login.LoginService;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unauth")
public class LoginController {

  private final LoginService loginService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
    return loginService.authenticate(loginDto);
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
    var userSeq = userService.register(registerUserDto);
    return ResponseEntity.created(
            URI.create("/api/auth/users/" + userSeq)
        )
        .build();
  }
}
