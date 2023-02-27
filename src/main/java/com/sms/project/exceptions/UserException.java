package com.sms.project.exceptions;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class UserException extends Exception {
    private String message;

    public UserException(String message) {
    super(message);
    }
}
