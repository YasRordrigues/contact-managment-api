package com.example.contactmanagementapi.presentener.advices;

import com.example.contactmanagementapi.application.dto.ErrorDto;
import com.example.contactmanagementapi.domain.errors.InvalidDataException;
import com.example.contactmanagementapi.domain.errors.ResourceAlreadyExistsException;
import com.example.contactmanagementapi.domain.errors.ResourceNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String combinedErrorMessage = String.join(", ", errorMessages);

        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, combinedErrorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + ex.getMessage());
    }

    private ResponseEntity<ErrorDto> buildErrorResponse(HttpStatus status, String message) {
        ErrorDto errorDto = new ErrorDto(message, status.value());
        return new ResponseEntity<>(errorDto, status);
    }
}

