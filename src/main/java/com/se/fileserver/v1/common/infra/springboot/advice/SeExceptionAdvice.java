package com.se.fileserver.v1.common.infra.springboot.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.se.fileserver.v1.common.domain.exception.SeException;
import com.se.fileserver.v1.common.presentation.response.ExceptionResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SeExceptionAdvice {

  @ExceptionHandler(SeException.class)
  public ResponseEntity<ExceptionResponse> handleSeException(final SeException e) {
    this.countExceptionAndLog(e);
    HttpStatus status = e.getHttpStatus();
    if (status == null)
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(ExceptionResponse.of(e), status);
  }

  // When precondition failed
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ExceptionResponse> handleConstraintViolationException(final Exception e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.PRECONDITION_FAILED);
  }

  // When input is invalid value
  @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<ExceptionResponse> handleBindException(final BindException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.BAD_REQUEST);
  }

  // When method is not allowed
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.METHOD_NOT_ALLOWED);
  }

  // When unauthorized
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(final AccessDeniedException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e), HttpStatus.UNAUTHORIZED);
  }

  // When json parse exception
  @ExceptionHandler(JsonParseException.class)
  protected ResponseEntity<ExceptionResponse> handleJsonParseException(final JsonParseException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e, "Json 파싱에 실패했습니다."), HttpStatus.BAD_REQUEST);
  }

  // When invalid enum input exception
  @ExceptionHandler(InvalidFormatException.class)
  protected ResponseEntity<ExceptionResponse> handleInvalidFormatException(final InvalidFormatException e) {
    this.countExceptionAndLog(e);
    return new ResponseEntity<>(ExceptionResponse.of(e, "유효하지 않은 enum 입니다."), HttpStatus.BAD_REQUEST);
  }

  private void countExceptionAndLog(final Exception e) {
    // TODO: Stack trace 등의 정보는 로그 스택에 저장
    e.printStackTrace();
//    log.error("{}: {}", e.getClass().getSimpleName(), e.getLocalizedMessage());
//    log.debug("{}: {}", e.getClass().getCanonicalName(), e.getMessage(), e);
  }
}