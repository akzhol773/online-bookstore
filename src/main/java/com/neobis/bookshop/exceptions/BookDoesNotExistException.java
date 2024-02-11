package com.neobis.bookshop.exceptions;

public class BookDoesNotExistException extends RuntimeException{
    public BookDoesNotExistException(String message) {
        super(message);
    }
}