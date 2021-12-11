package com.oyo.topscore.utils;

public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
