package kr.co.mz.sns.controller.auth;

import jakarta.validation.Valid;
import kr.co.mz.sns.config.security.CustomUserDetails;
import kr.co.mz.sns.config.security.JWTService;
import kr.co.mz.sns.dto.login.LoginDto;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unauth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtService.generateToken(authentication);
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(
                        "Log-In Succeed : " + userService.updateLastLogin(customUserDetails.getUserDto().getSeq()).toString());
    }

}
