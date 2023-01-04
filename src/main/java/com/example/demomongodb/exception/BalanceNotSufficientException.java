package com.example.demomongodb.exception;

import lombok.Builder;

@Builder
public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException() {
        super();
    }

    public BalanceNotSufficientException(String message) {
        super(message);
    }

}
