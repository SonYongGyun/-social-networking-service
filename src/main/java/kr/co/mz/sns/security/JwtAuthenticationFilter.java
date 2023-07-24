package kr.co.mz.sns.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final CustomUserDetailService customUserDetailService;

  @Autowired
  public JwtAuthenticationFilter(JWTService jwtService, CustomUserDetailService customUserDetailService) {
    this.jwtService = jwtService;
    this.customUserDetailService = customUserDetailService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    // 토큰이 유효하다면
    var token = getJWTFromRequest(request).orElseGet(() -> "");
    if (StringUtils.hasText(token) && jwtService.validateToken(token)) {
      // 이 토큰을 key 로 파싱을 진행하여 사용자 이름을 얻음
      var email = jwtService.getEmailJWT(token);
      var userDetails = customUserDetailService.loadUserByUsername(email);
      // 사용자의 인증 정보를 나타내는 인터페이스 생성 : principal(사용자주체), credentials(자격증명)
      var authenticationToken
          = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
          userDetails.getAuthorities());
      // IP주소, 브라우저 정보, 인증 시간 등과 같은 웹 기반 인증에 필요한 정보를 담음
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      // 이후의 보안 관련 작업에서 해당 사용자의 정보를 사용할 수 있도록 사용자 인증 정보 셋팅
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> getJWTFromRequest(HttpServletRequest request) {
    var bearerToken = request.getHeader("Authorization");
    String token = null;
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      token = bearerToken.substring(7);
    }
    return Optional.ofNullable(token);
  }
}
