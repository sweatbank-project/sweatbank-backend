package com.sweaterbank.leasing.car.advice;

import com.sweaterbank.leasing.car.exceptions.AccountExistsException;
import com.sweaterbank.leasing.car.exceptions.MailDataNotFoundException;
import com.sweaterbank.leasing.car.exceptions.NotMatchingPasswordsException;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.exceptions.UserNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<ApiError> handleAccountExistsException(AccountExistsException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(PendingLeasesException.class)
    public ResponseEntity<ApiError> handlePendingLeasesException(PendingLeasesException ex, WebRequest request){
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(MailDataNotFoundException.class)
    public ResponseEntity<ApiError> handleMailDataNotFoundException(MailDataNotFoundException ex, WebRequest request){
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
  
    @ExceptionHandler(NotMatchingPasswordsException.class)
    public ResponseEntity<ApiError> handleNotMatchingPasswordsException(ValidationException ex,
                                                                        WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                          WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(errors),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
