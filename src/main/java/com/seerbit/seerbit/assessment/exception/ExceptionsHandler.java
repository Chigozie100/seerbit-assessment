package com.seerbit.seerbit.assessment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        String message = errors.entrySet().iterator().next().getValue();
        if (message != null && (message.toLowerCase().contains("java") || message.toLowerCase().contains("exception")
                || message.toLowerCase().contains("spring"))
        ) {
            message = "An error occurred";
        }
        ExceptionModel exceptionModel = new ExceptionModel(400, message, errors);

        log.error(exceptionModel.toString());
        logStackTrace(ex);
        return ResponseEntity.status(400).body(exceptionModel);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionModel> handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex.getMessage());
        logStackTrace(ex);
        return ResponseEntity.status(404).body(new ExceptionModel(404, "Data not found"));
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionModel> handleCustomException(CustomException ex) {

        logStackTrace(ex);
        ExceptionModel exceptionModel = new ExceptionModel(
                ex.getHttpStatusCode(), ex.getMessage(), ex.getErrors()
        );

        log.error(exceptionModel.toString());
        return ResponseEntity.status(ex.getHttpStatusCode()).body(exceptionModel);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionModel> handleAnyException(Exception e) {
        log.error(e.getMessage());
        logStackTrace(e);
        return ResponseEntity.status(500).body(new ExceptionModel(500, "An error occurred"));
    }

    private void logStackTrace(Exception e) {
		e.printStackTrace();
    }
}