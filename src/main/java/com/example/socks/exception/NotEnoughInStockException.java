package com.example.socks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotEnoughInStockException extends RuntimeException {
    public NotEnoughInStockException(String message) {
        super(message);
    }
}
