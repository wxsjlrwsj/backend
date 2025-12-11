package org.example.chaoxingsystem.user;

import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * 全局异常处理：统一返回 ApiResponse 格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
    int code = ex.getStatusCode().value();
    String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
    log.warn("ResponseStatusException: status={}, message={}", code, message);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    int code = 400;
    String message = ex.getBindingResult().getFieldError() != null ? ex.getBindingResult().getFieldError().getDefaultMessage() : "参数错误";
    log.warn("MethodArgumentNotValidException: {}", message);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
    int code = 400;
    String message = ex.getBindingResult().getFieldError() != null ? ex.getBindingResult().getFieldError().getDefaultMessage() : "参数错误";
    log.warn("BindException: {}", message);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    int code = 400;
    String message = "请求体解析失败";
    log.warn("HttpMessageNotReadableException", ex);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
    int code = 500;
    String message = "服务器错误";
    log.error("Unhandled exception", ex);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    int code = 400;
    String message = "用户名或邮箱已存在";
    log.warn("DataIntegrityViolationException", ex);
    return ResponseEntity.status(code).body(ApiResponse.error(code, message));
  }
}
