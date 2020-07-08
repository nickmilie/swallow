package com.homework.reportapi.exception;

public class ReportNotFoundException extends RuntimeException{

    public ReportNotFoundException() {
    }

    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReportNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
