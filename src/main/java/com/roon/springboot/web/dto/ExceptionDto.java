package com.roon.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionDto {
    private String code;
    private String message;
}
