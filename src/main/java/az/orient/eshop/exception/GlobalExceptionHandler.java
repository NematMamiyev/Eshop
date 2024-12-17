package az.orient.eshop.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EshopException.class)
    public ResponseEntity<ExceptionResponse> handleException(EshopException ex){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException ex){
        var fieldErrors= ex.getFieldErrors().stream()
                .map(f->FieldError.builder()
                        .fieldName(f.getField())
                        .message(f.getDefaultMessage())
                        .build()).toList();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage())
                .fieldErrors(fieldErrors)
                .build();
        return ResponseEntity.status(exceptionResponse.getCode()).body(exceptionResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
