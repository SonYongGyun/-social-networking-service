package kr.co.mz.sns.controller;

import kr.co.mz.sns.dto.ErrorDto;
import kr.co.mz.sns.exception.FileWriteException;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.exception.ResourceAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception e) {
    var errorDto = new ErrorDto("An error occurred: " + e.getMessage(), 500);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
  }

  @ExceptionHandler(
      {
          BadRequest.class,
          MethodArgumentNotValidException.class,
          ResourceAlreadyExistsException.class,
          IllegalArgumentException.class
      })
  public ResponseEntity<ErrorDto> handleBadRequestException(Exception e) {
    var errorDto = new ErrorDto(e.getMessage(), 400);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
  }

  @ExceptionHandler({AuthenticationException.class, HttpClientErrorException.Unauthorized.class})
  public ResponseEntity<ErrorDto> handleAuthenticationException(Exception e) {
    var errorDto = new ErrorDto("맞지않는 아이디이거나 맞지않는 비밀번호입니다: " + e.getMessage(), 401);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
    ErrorDto errorDto = new ErrorDto("응~안돼~ 돌아가~: " + e.getMessage(), 403);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException nfe) {
    var errorDto = new ErrorDto("정보를 찾지 못했습니다: " + nfe.getMessage(), 404);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }

  @ExceptionHandler(FileWriteException.class)
  public ResponseEntity<ErrorDto> handleFailedSaveFileException(FileWriteException fsfe) {
    var errDto = new ErrorDto("파일등록에 실패하였습니다" + fsfe.getMessage(), 500);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errDto);
  }
}
