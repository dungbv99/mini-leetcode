package com.hust.minileetcode.exception;

import com.hust.minileetcode.exception.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleServerError(final Exception ex, final WebRequest request){
        log.error("[Internal_Server_Error] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(ExceptionResponse.builder().code(500).message(ex.getMessage()).build());
    }

    @ExceptionHandler({MiniLeetCodeException.class})
    protected ResponseEntity<Object> handleBadRequest(final MiniLeetCodeException ex, final WebRequest request){
        log.error("[Bad_Request] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(ExceptionResponse.builder().code(400).message(ex.getMessage()).build());
    }
}
