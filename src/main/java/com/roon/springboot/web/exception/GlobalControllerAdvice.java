package com.roon.springboot.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        log.info("exception catched");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgException(IllegalArgumentException illegalArgException) {
        log.info("IllegalArgumentException catched");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegalArgException.getMessage());
    }

    @ExceptionHandler(value = NotEnoughMoneyException.class)
    public NotEnoughMoneyException notEnoughMoneyException(NotEnoughMoneyException notEnoughMoneyException) {
        log.info("통장 잔고가 모자라요....");
        return new NotEnoughMoneyException("통장 잔고가 모자라요", HttpStatus.BAD_REQUEST);
    }
}
