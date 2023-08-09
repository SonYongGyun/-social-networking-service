package kr.co.mz.sns.controller.auth;

import kr.co.mz.sns.config.security.JWTService;
import kr.co.mz.sns.dto.login.LoginDto;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/unauth/jwt")
public class JwtRequestController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CurrentUserInfo currentUserInfo;

    @PostMapping
    public ResponseEntity<String> getJwtString(@RequestBody LoginDto loginDto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        currentUserInfo.setAuth(authentication);
        return ResponseEntity.ok()
                .body(
                        jwtService.generateToken(authentication)
                );
    }
}
