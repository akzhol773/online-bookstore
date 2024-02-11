package com.neobis.bookshop.exceptions;

public class UnableToGetTokenException extends RuntimeException{
    public UnableToGetTokenException(String message) {
        super(message);
    }
}