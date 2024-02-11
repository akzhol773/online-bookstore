package com.neobis.bookshop.exceptions;

public class AccountDoesNotExistException extends RuntimeException{
    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
