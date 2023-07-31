package kr.co.mz.sns.controller.login;

import jakarta.validation.Valid;
import kr.co.mz.sns.config.security.JWTService;
import kr.co.mz.sns.dto.login.LoginDto;
import kr.co.mz.sns.dto.login.RegisterDto;
import kr.co.mz.sns.repository.user.UserRepository;
import kr.co.mz.sns.service.user.UserDetailService;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unauth")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserDetailService userDetailService;
  private final JWTService jwtService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    var token = jwtService.generateToken(authentication);
    var headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);

    var lastLoginTime = userService.lastLogin(loginDto.getEmail());

    return ResponseEntity
        .ok()
        .headers(headers)
        .body("Log-In Succeed : " + lastLoginTime.toLocalTime().toString());
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
    if (userRepository.existsByEmail(registerDto.getEmail())) {
      return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
    }
    userService.register(registerDto);
    return ResponseEntity
        .ok("User registered Success!");
  }
}
