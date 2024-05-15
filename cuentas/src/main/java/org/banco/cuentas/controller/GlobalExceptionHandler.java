package org.banco.cuentas.controller;

import org.banco.cuentas.model.CustomException;
import org.banco.cuentas.model.ErrorCode;
import org.springframework.dao.DataAccessException;
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
        STATUS_MAP.put(ErrorCode.ACCOUNT_ALREADY_EXISTS, HttpStatus.CONFLICT);
        STATUS_MAP.put(ErrorCode.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
        STATUS_MAP.put(ErrorCode.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        STATUS_MAP.put(ErrorCode.OPERATION_NOT_FOUND, HttpStatus.NOT_FOUND);

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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return new ResponseEntity<>("Error en la base de datos: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}