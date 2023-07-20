package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.AuthResponseDto;
import kr.co.mz.sns.dto.LoginDto;
import kr.co.mz.sns.dto.RegisterDto;
import kr.co.mz.sns.entity.UserEntity;
import kr.co.mz.sns.enums.Role;
import kr.co.mz.sns.repository.UserRepository;
import kr.co.mz.sns.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  @Autowired
  public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository,
      PasswordEncoder passwordEncoder, JWTService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }


  @RequestMapping("login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getUserName(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    var token = jwtService.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
  }

  @RequestMapping("register")//등록이니까 저장되지 멍충아...
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    if (userRepository.existsByName(registerDto.getUserName())) {
      return new ResponseEntity<>("UserName is taken!", HttpStatus.BAD_REQUEST);
    }
    var user = new UserEntity();
    user.setName(registerDto.getUserName());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setRole(Role.ROLE_MEMBER.toString());
    userRepository.save(user);
    return new ResponseEntity<>("User registered Success!", HttpStatus.OK);
  }

}
/*
유저가 들어오면 필터체인거쳐서 들어온다.
토큰 있는지 확인하고
컨트롤러로 간다.
인증되면 JWT만들어주고
로그인정보와 토큰가지고 돌아간다.
 */