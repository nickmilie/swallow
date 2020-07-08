package com.homework.reportapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReportRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ReportErrorResponse> handleReportNotFoundException(ReportNotFoundException exception){

        ReportErrorResponse reportErrorResponse = new ReportErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(reportErrorResponse, HttpStatus.NOT_FOUND);
    }
}
