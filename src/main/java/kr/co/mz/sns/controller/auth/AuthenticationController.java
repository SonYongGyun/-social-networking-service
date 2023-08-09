package kr.co.mz.sns.controller.auth;

import jakarta.validation.Valid;
import kr.co.mz.sns.dto.login.LoginDto;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unauth")
public class AuthenticationController {

    private static final String JWT_URL = "http://172.90.4.143:8080/api/unauth/jwt";//todo jwt generator
    private final UserService userService;
    private final RestTemplate restTemplate;

    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        var responsedJwt = restTemplate.postForEntity(JWT_URL, loginDto, String.class);
        var token = responsedJwt.getBody();
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(
                        "Log-In Succeed : " + userService.updateLastLogin().toString());
    }

}
