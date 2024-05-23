package com.example.demo.CommonClasses.Exceptions;

public class UnachievableServer extends Exception{
    public UnachievableServer() {
    }

    public UnachievableServer(String message) {
        super(message);
    }

    public UnachievableServer(Throwable cause) {
        super(cause);
    }

    public UnachievableServer(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnachievableServer(String message, Throwable cause) {
        super(message, cause);
    }
}
