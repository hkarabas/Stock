package com.payconiq.stock.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception exception) {
        log.error("Unhandled exception occurred: ", exception);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<Object> handleStockNotFoundException(Exception exception) {
        log.error("Stock Not Found ", exception);
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler({JsonPatchException.class, JsonProcessingException.class})
    public ResponseEntity<Object> convertJsonException(Exception exception) {
        log.error("Json convert exception ", exception);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    public ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception t) {
        return ResponseEntity.status(httpStatus).body(StockError.builder()
                .code(httpStatus.value())
                .message(t.getMessage())
                .cause(t.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
