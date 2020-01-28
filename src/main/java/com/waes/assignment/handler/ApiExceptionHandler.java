package com.waes.assignment.handler;

import com.waes.assignment.exception.BothSideNotFoundException;
import com.waes.assignment.exception.DiffNotFoundException;
import com.waes.assignment.exception.InvalidSideException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides handling exception for APIs
 *
 * @developer -- ilkercolakoglu
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DiffNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundExceptions(HttpServletRequest request, Exception exception) {
        ApiError apiError = ApiError.builder()
                .cause(exception.getMessage())
                .uri(request.getRequestURI())
                .timestamp(new Date().toString())
                .status(HttpStatus.NOT_FOUND.value()).build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidSideException.class})
    public ResponseEntity<ApiError> handleUnprocessableEntity(HttpServletRequest request, Exception exception) {
        ApiError apiError = ApiError.builder()
                .cause(exception.getMessage())
                .uri(request.getRequestURI())
                .timestamp(new Date().toString())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value()).build();

        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BothSideNotFoundException.class})
    public ResponseEntity<ApiError> handlePreconditionRequiredExceptions(HttpServletRequest request, Exception exception) {

        ApiError apiError = ApiError.builder()
                .cause(exception.getMessage())
                .uri(request.getRequestURI())
                .timestamp(new Date().toString())
                .status(HttpStatus.PRECONDITION_REQUIRED.value()).build();

        return new ResponseEntity<>(apiError, HttpStatus.PRECONDITION_REQUIRED);
    }

    private ResponseEntity<ApiError> handleErrorResponse(HttpServletRequest request, Exception exception,
                                                         HttpStatus status) {
        ApiError apiError = ApiError.builder()
                .cause(exception.getMessage())
                .uri(request.getRequestURI())
                .timestamp(new Date().toString())
                .status(status.value()).build();

        return new ResponseEntity<>(apiError, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = ApiError.builder()
                .cause(String.join(",", errors))
                .uri(request.getContextPath())
                .timestamp(new Date().toString())
                .status(status.value()).build();
        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

}
