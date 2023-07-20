package kr.co.mz.sns.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.mz.sns.dto.ErrorDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    response.setCharacterEncoding("utf-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    var dto = new ErrorDto("접근 권한이 없습니다 : " + authException.getMessage());
    response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
//    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "접근 권한이 없습니다 : " + authException.getMessage());
  }
}
