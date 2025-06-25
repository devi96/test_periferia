package com.gestion.alumno_service.exception;

import com.gestion.alumno_service.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlumnoDuplicadoException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlumnoDuplicado(AlumnoDuplicadoException ex) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setError("Conflict");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationError(WebExchangeBindException ex) {
        Map<String, Object> response = new HashMap<>();
        String message = ex.getFieldErrors().stream()
                .map(err -> Objects.requireNonNullElse(err.getDefaultMessage(), "Error de validación"))
                .findFirst()
                .orElse("Error de validación");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setError("Bad Request");
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}