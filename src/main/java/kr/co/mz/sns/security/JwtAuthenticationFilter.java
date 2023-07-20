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
    var token = getJWTFromRequest(request).orElseGet(() -> "");
    if (StringUtils.hasText(token) && jwtService.validateToken(token)) {
      var userName = jwtService.getUserNameFromJWT(token);
      var userDetails = customUserDetailService.loadUserByUsername(userName);
      var authenticationToken
          = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
