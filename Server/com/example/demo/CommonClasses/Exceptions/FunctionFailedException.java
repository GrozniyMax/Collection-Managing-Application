package com.example.demo.CommonClasses.Exceptions;

/**
 * Исключение, которое возникает при попытке выполнения функции, которая завершилась неудачно
 */
public class FunctionFailedException extends Exception{
    public FunctionFailedException(String message) {
        super(message);
    }

    public FunctionFailedException() {
    }
}
