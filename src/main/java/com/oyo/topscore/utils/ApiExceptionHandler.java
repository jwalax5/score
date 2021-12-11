package com.oyo.topscore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<Object> handleApiException(ApiException e, WebRequest request) {
        return ApiResponseHandler.generateFailureResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity handleBadRequest(Exception e, WebRequest request) {
        String s = e.getMessage();
        return ApiResponseHandler.generateFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);
        return ApiResponseHandler.generateFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }
}