package com.revie.apartments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorResponseDto errorResponseDto = new ErrorResponseDto();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        errorResponseDto.setStatus_code(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException ex) {
        errorResponseDto.setStatus_code(HttpStatus.CONFLICT.value());
        errorResponseDto.setError(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException ex) {
        errorResponseDto.setStatus_code(HttpStatus.UNAUTHORIZED.value());
        errorResponseDto.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex) {
        errorResponseDto.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorResponseDto.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponseDto> handleForbiddenException(ForbiddenException ex) {
        errorResponseDto.setStatus_code(HttpStatus.FORBIDDEN.value());
        errorResponseDto.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException ex) {
        errorResponseDto.setStatus_code(HttpStatus.UNAUTHORIZED.value());
        errorResponseDto.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing file: " + ex.getMessage());
    }
}
