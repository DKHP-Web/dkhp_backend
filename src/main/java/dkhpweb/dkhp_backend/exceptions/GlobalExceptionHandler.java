package dkhpweb.dkhp_backend.exceptions;

import dkhpweb.dkhp_backend.dtos.ErrorDto;
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
    public ResponseEntity<ErrorDto> handleBadRequestException(Exception ex){
        log.error("A bad request exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        var errorDto= new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex){
        log.error("An forbidden exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        var errorDto= new ErrorDto(HttpStatus.FORBIDDEN.value(), "Forbidden", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ErrorDto> handleUnAuthorizedException(Exception ex){
        log.error("An unauthorized exception occurred: {}", ex.getMessage());
        var errors= List.of(ex.getMessage());
        var errorDto= new ErrorDto(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDto> handleValidationException(BindException ex){
        log.error("An validation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.add(error.getField()+": "+error.getDefaultMessage());
        });
        var errorDto= new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintException(ConstraintViolationException ex){
        log.error("An violation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for(var violation : violations){
            errors.add(violation.getPropertyPath().toString()+": "+violation.getMessage());
        }
        var errorDto= new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDto> handleHandlerMethodValidationException(HandlerMethodValidationException ex){
        log.error("An validation exception occurred: {}", ex.getMessage());
        var errors= new ArrayList();
        ex.getValueResults().forEach(result ->
                result.getResolvableErrors().forEach(error ->
                        errors.add(((FieldError) error).getField()+":"+error.getDefaultMessage())
                )
        );
        var errorDto= new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnknownException(Exception ex){
        log.error("An unexpected exception occurred: {}", ex.getMessage());
        var errorDto= new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Bad Request", List.of("Unknown error"));
        return ResponseEntity.ok(errorDto);
    }
}
