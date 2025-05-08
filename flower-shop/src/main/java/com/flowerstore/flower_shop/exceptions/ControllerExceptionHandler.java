package com.flowerstore.flower_shop.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApiExceptionResponse.class)
    protected ResponseEntity<Object> handleApiExceptionResponse(ApiExceptionResponse ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .errors(ex.getErrors())
                .status(status)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementResponse(NoSuchElementException ex) {
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Elementul nu a fost găsit: " + ex.getMessage())
                .errors(Collections.singletonList("Elementul specificat nu există."))
                .build());
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .errors(List.of("Invalid input provided."))
                .build());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserExists(UserAlreadyExistsException ex) {
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .errors(List.of("Email duplicat"))
                .build());
    }


    private ResponseEntity<Object> responseEntityBuilder(ApiExceptionResponse ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}
