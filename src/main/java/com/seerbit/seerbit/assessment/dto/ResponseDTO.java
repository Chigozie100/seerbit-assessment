package com.seerbit.seerbit.assessment.dto;

public record ResponseDTO<T>(

        String status,

        String message,

        T data
) {
    public ResponseDTO(String message) {
        this("success", message, null);
    }

    public ResponseDTO(String message, T data) {
        this("success", message, data);
    }
}