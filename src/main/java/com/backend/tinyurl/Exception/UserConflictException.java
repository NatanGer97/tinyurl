package com.backend.tinyurl.Exception;

public class UserConflictException extends RuntimeException {
    public UserConflictException(String message) {
        super(message);
    }


}
