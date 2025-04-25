package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        // Collect all field errors
        List<Map<String,String>> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fe -> {
                Map<String,String> err = new HashMap<>();
                err.put("field", fe.getField());
                err.put("message", fe.getDefaultMessage());
                return err;
            })
            .collect(Collectors.toList());

        // Build response body
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);
        body.put("path", request.getRequestURI());
        return body;
    }
}
