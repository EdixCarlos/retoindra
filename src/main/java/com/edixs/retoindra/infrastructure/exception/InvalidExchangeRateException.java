package com.edixs.retoindra.infrastructure.exception;

public class InvalidExchangeRateException extends RuntimeException {
    public InvalidExchangeRateException(String message) {
        super(message);
    }
}