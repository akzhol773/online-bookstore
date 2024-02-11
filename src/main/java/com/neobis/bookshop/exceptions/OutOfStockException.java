package com.neobis.bookshop.exceptions;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }
}