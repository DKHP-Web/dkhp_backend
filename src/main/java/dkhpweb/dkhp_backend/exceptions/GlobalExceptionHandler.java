package dkhpweb.dkhp_backend.exceptions;

import dkhpweb.dkhp_backend.dtos.ApiResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResult> handleBadRequestException(Exception ex){
        log.error("A bad request exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResult> handleAccessDeniedException(Exception ex){
        log.error("An forbidden exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ApiResult> handleUnAuthorizedException(Exception ex){
        log.error("An unauthorized exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResult> handleValidationException(BindException ex){
        log.error("An validation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.add(error.getField()+": "+error.getDefaultMessage());
        });
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResult> handleConstraintException(ConstraintViolationException ex){
        log.error("An violation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for(var violation : violations){
            errors.add(violation.getPropertyPath().toString()+": "+violation.getMessage());
        }
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResult> handleHandlerMethodValidationException(HandlerMethodValidationException ex){
        log.error("An validation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        ex.getValueResults().forEach(result ->
                result.getResolvableErrors().forEach(error ->
                        errors.add(((FieldError) error).getField()+":"+error.getDefaultMessage())
                )
        );
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult> handleUnknownException(Exception ex){
        log.error("An unexpected exception occurred: {}", ex.getMessage());
        var errors= List.of("Unknown error");
        return ResponseEntity.ok(ApiResult.failure(errors, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
