package com.sms.project.exceptions;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ProductException extends Exception {
    private String message;

    public ProductException(String message) {
    super(message);
    }
}
