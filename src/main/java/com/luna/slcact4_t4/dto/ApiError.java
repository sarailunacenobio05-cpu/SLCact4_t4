package com.luna.slcact4_t4.dto;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors) {

    public static ApiError of(int status, String error, String message, String path,
            Map<String, String> fieldErrors) {
        return new ApiError(Instant.now().toString(), status, error, message, path, fieldErrors);
    }
}
