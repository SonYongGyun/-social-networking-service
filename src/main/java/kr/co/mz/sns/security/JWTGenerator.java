package kr.co.mz.sns.security;

import static kr.co.mz.sns.security.SecurityConstants.JWT_EXPIRATION;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {

  // 토큰은 3가지 부분으로 나눠지기 떄문에 필요한 부분들 작성하는거 만들어줬다.
  SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(Authentication authentication) {
    var userName = authentication.getName();
    var currentDate = new Date();
    var expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

    return Jwts.builder()
        .setSubject(userName)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(key)
        .compact();
  }


  /**
   * 제시하신 getUserNameFromJWT 메서드는 JWT 토큰에서 'subject'라는 클레임을 추출하는 역할을 합니다. 이 'subject' 클레임은 일반적으로 사용자의 고유 식별자(예를 들어 사용자
   * 이름, 이메일, 사용자 ID 등)를 담고 있습니다.
   * <p>
   * 즉, 이 메서드는 주어진 JWT 토큰이 어떤 사용자에 대한 정보를 포함하고 있는지를 확인하는데 사용됩니다. 이를 통해 서버는 클라이언트의 요청에 대한 인증을 수행하고, 해당 사용자에 대한 권한을 검증하며,
   * 필요한 경우 사용자에 대한 추가적인 처리를 수행할 수 있습니다.
   * <p>
   * 예를 들어, 웹 애플리케이션에서 클라이언트는 로그인을 한 후 JWT 토큰을 받게 됩니다. 그 후 클라이언트는 이 토큰을 이용해 인증이 필요한 요청을 서버에 보냅니다. 이때 서버는
   * getUserNameFromJWT 메서드를 사용하여 토큰에서 사용자 식별자를 추출하고, 이를 바탕으로 해당 요청이 유효한 사용자에 의해 발생된 것임을 확인할 수 있습니다.
   *
   * @param token
   * @return
   */
  public String getUserNameFromJWT(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
    }
  }
}
