package com.roon.springboot.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoStockException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
