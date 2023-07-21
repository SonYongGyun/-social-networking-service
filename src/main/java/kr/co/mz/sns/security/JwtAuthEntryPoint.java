package kr.co.mz.sns.security;

//@Component
//public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
//
//  @Override
//  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//      throws IOException, ServletException {
//    response.setCharacterEncoding("utf-8");
//    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    response.setContentType("application/json");
//    var dto = new ErrorDto("접근 권한이 없습니다 에?????????????????????????: " + authException.getMessage());
//    response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
//    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "접근 권한이 없습니다 : " + authException.getMessage());
//  }
//}
