package com.luna.slcact4_t4.dto;

import com.luna.slcact4_t4.entity.AppUser;

public record UserResponse(Long id, String name, String email, String role) {

    public static UserResponse from(AppUser u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name());
    }
}
