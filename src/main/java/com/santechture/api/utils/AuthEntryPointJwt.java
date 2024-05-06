package com.santechture.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santechture.api.dto.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex)
            throws IOException {
        log.error("Unauthorized error: {}", ex.getMessage());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(res.getOutputStream(), new GeneralResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                null,
                ex.getMessage()
        ));
    }
}
