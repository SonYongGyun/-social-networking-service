package kr.co.mz.sns.controllers;

import kr.co.mz.sns.dto.ErrorDto;
import kr.co.mz.sns.exception.PostNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    var errorDto = new ErrorDto("An error occurred: " + e.getMessage());
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

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ErrorDto> handlePostNotFoundException(PostNotFoundException pnfe) {
    ErrorDto errorDto = new ErrorDto(pnfe.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ErrorDto> handleErrorDtoResponseEntity(EmptyResultDataAccessException erde) {
    var errorDto = new ErrorDto("유저의 상세 정보를 찾지 못했습니다: " + erde.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
  }
}