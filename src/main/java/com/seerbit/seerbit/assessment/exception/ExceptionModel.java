package com.seerbit.seerbit.assessment.exception;

import java.util.Map;

public record ExceptionModel(int httpStatusCode, String status, String message, Map<String, String> errors) {

    public ExceptionModel(int httpStatusCode, String message) {
        this(httpStatusCode, "failed", message, null);
    }

    public ExceptionModel(int httpStatusCode, String message, Map<String, String> errors) {
        this(httpStatusCode, "failed", message, errors);
    }
}
