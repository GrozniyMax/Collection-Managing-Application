package com.example.demo.CommonClasses.Exceptions;

/**
 * Исключение, которое возникает при попытке выполнения функции с некорректными вводными данными
 */
public class InvalidInputException extends RuntimeException{

    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);

    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);

    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }

}
