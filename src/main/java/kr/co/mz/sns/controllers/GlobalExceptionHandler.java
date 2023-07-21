package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception e) {
    var errorDto = new ErrorDto("아! 또 무슨 에런데!: " + e.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException e) {
    var errorDto = new ErrorDto("맞지않는 아이디이거나 맞지않는 비밀번호입니다: " + e.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
    ErrorDto errorDto = new ErrorDto("응~안돼~ 돌아가~: " + e.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
  }
}
