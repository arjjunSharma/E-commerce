package com.ecommerce.user.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {
  // this method runs whenever ResourceAlreadyExistsException is thrown

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<Map<String, Object>> handleExits(ResourceAlreadyExistsException ex) {
    Map<String, Object> m = Map.of(
        "error", "Conflict", "message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(m);

  }

  // this method runs whenever ResourceNotFoundException is thrown
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotfound(ResourceNotFoundException ex) {
    Map<String, Object> m = Map.of("error", "Not Found", "message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
  }

  // handleer method for method argument not valid
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, Object> error = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));

    Map<String, Object> m = Map.of("error", "Validation Failed", "details", "error");
    return ResponseEntity.badRequest().body(m);

  }

  // here we are dealing all other remaning exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
    Map<String, Object> m = Map.of("error", "Internal Server Error", "message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(m);
  }

}