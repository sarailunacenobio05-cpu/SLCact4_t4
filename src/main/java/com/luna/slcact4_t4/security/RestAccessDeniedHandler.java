package com.luna.slcact4_t4.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String path = request.getRequestURI().replace("\\", "").replace("\"", "");
        String json = "{\"timestamp\":\"" + Instant.now() + "\","
                + "\"status\":403,"
                + "\"error\":\"Forbidden\","
                + "\"message\":\"No tienes permiso para acceder a este recurso\","
                + "\"path\":\"" + path + "\"}";
        response.getWriter().write(json);
    }
}
