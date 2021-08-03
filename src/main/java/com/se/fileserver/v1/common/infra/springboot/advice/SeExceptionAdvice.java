package com.se.fileserver.v1.common.infra.springboot.advice;

import com.se.fileserver.v1.common.domain.exception.PreconditionFailedException;
import com.se.fileserver.v1.common.domain.exception.SeException;
import com.se.fileserver.v1.common.presentation.response.ExceptionResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SeExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleSeException(final SeException e){
    this.countExceptionAndLog(e);
    HttpStatus status = e.getHttpStatus();
    if(status == null)
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(ExceptionResponse.of(e), status);
  }

  @ExceptionHandler(SeException.class)
  public ResponseEntity<ExceptionResponse> handleGenieCheckedException(final SeException e) {
    this.countExceptionAndLog(e);
    if (e.getHttpStatus() == null)
      return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(ExceptionResponse.of(e), e.getHttpStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ExceptionResponse> handleConstraintViolation(final ConstraintViolationException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.PRECONDITION_FAILED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.PRECONDITION_FAILED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(final AccessDeniedException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.UNAUTHORIZED);
  }

  private void countExceptionAndLog(final Exception e) {
    // TODO: Stack trace 등의 정보는 로그 스택에 저장
//    log.error("{}: {}", e.getClass().getSimpleName(), e.getLocalizedMessage());
//    log.debug("{}: {}", e.getClass().getCanonicalName(), e.getMessage(), e);
  }
}