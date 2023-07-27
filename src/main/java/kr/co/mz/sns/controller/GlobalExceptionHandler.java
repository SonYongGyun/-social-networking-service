package kr.co.mz.sns.controller;

import kr.co.mz.sns.dto.ErrorDto;
import kr.co.mz.sns.exception.FailedSaveFileException;
import kr.co.mz.sns.exception.NotFoundException;
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
        var errorDto = new ErrorDto("An error occurred: " + e.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException e) {
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

    @ExceptionHandler(FailedSaveFileException.class)
    public ResponseEntity<ErrorDto> handleFailedSaveFileException(FailedSaveFileException fsfe) {
        var errDto = new ErrorDto("파일등록에 실패하였습니다" + fsfe.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errDto);
    }
}
