package com.luna.slcact4_t4.dto;

public record AuthResponse(String token, String tokenType, long expiresInSeconds, UserResponse user) {
}
