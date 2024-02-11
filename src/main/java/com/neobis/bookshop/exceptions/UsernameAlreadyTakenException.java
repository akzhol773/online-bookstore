package com.neobis.bookshop.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
