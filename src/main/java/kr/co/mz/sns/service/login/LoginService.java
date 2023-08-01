package kr.co.mz.sns.service.login;

import kr.co.mz.sns.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginService {

    private static final String AUTH_URL = "http://localhost:8080/api/unauth/authenticate";
    private final RestTemplate restTemplate;

    public ResponseEntity<String> authenticate(LoginDto loginDto) {
        return restTemplate.postForEntity(AUTH_URL, loginDto, String.class);
    }
}
