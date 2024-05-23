package com.example.demo.ScriptExecution;

public class ScriptExecutionFailed extends Exception {
    public ScriptExecutionFailed() {
    }

    public ScriptExecutionFailed(String message) {
        super(message);
    }

    public ScriptExecutionFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptExecutionFailed(Throwable cause) {
        super(cause);
    }

    public ScriptExecutionFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
