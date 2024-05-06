package com.santechture.api.exception;

import com.santechture.api.dto.GeneralResponse;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
@ControllerAdvice
@Priority(0)
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BusinessExceptions.class)
    public ResponseEntity<GeneralResponse> handleException(BusinessExceptions e, HttpServletRequest request, Locale locale) {
        GeneralResponse response = new GeneralResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(messageSource.getMessage(e.getMessage(), null, locale));
        response.setTotal(null);
        response.setData(null);
        return response.response(response.getCode(), response.getMessage(), HttpStatus.BAD_REQUEST);
    }

}