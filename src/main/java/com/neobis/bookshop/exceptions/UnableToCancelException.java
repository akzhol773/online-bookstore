package com.neobis.bookshop.exceptions;

public class UnableToCancelException extends RuntimeException{
    public UnableToCancelException(String message) {
        super(message);
    }
}
