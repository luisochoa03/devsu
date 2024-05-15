package org.banco.controller;

import org.banco.model.CustomException;
import org.banco.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.EnumMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Map<ErrorCode, HttpStatus> STATUS_MAP = new EnumMap<>(ErrorCode.class);

    static {
        STATUS_MAP.put(ErrorCode.CLIENT_ALREADY_EXISTS, HttpStatus.CONFLICT);
        STATUS_MAP.put(ErrorCode.CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        STATUS_MAP.put(ErrorCode.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        HttpStatus status = STATUS_MAP.getOrDefault(ex.getErrorCode(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
