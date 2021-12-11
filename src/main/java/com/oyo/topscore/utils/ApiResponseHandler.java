package com.oyo.topscore.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ApiResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("result", responseObj);

        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateFailureResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("error", responseObj);

        return new ResponseEntity<Object>(map, status);
    }
}
