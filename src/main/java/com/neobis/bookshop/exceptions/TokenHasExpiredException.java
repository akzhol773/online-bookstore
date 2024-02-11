package com.neobis.bookshop.exceptions;

public class TokenHasExpiredException extends RuntimeException{
    public TokenHasExpiredException(String message) {
        super(message);
    }
}