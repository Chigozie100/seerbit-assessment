package com.seerbit.seerbit.assessment.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private int httpStatusCode;
    private String message;
    private Map<String, String> errors;

    public CustomException(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public static void customExceptions(String message, HttpServletResponse response) throws IOException {
        response.setHeader("Error_Message", message);
        Map<String, String> error = new HashMap<>();
        error.put("Error_Message", message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
