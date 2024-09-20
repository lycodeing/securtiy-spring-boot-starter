package com.lycodeing.security.exception;

public class RoleDeniedException extends RuntimeException{
    public RoleDeniedException(String message) {
        super(message);
    }

    public RoleDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
