package com.roon.springboot.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

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

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException e) {
        log.info("entity 없음");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = InterruptedException.class)
    public void 귀찮군() {

    }

    @ExceptionHandler(value = RuntimeException.class)
    public void 귀찮군2(RuntimeException e) {
        log.info(e.getMessage());
    }
}
