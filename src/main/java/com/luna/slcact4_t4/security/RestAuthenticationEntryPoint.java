package com.luna.slcact4_t4.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("WWW-Authenticate", "Bearer");
        String path = request.getRequestURI().replace("\\", "").replace("\"", "");
        String json = "{\"timestamp\":\"" + Instant.now() + "\","
                + "\"status\":401,"
                + "\"error\":\"Unauthorized\","
                + "\"message\":\"Autenticación requerida: token JWT ausente, inválido o expirado\","
                + "\"path\":\"" + path + "\"}";
        response.getWriter().write(json);
    }
}
