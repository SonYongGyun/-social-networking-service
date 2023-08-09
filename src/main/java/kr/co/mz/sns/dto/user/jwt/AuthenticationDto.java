package kr.co.mz.sns.dto.user.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

    private Authentication authentication;
    
}
