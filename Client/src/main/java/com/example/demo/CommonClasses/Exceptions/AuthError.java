package com.example.demo.CommonClasses.Exceptions;

public class AuthError extends Exception {

    public AuthError(String message) {
        super(message);
    }

    public AuthError() {
    }

    public AuthError(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthError(Throwable cause) {
        super(cause);
    }

    public AuthError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
