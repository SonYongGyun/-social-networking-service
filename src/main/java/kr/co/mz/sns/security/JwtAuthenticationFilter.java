package kr.co.mz.sns.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private CustomUserDetailService customUserDetailService;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getJWTFromRequest(request);
    if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
      var userName = jwtGenerator.getUserNameFromJWT(token);
      UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
      UsernamePasswordAuthenticationToken authenticationToken
          = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}
