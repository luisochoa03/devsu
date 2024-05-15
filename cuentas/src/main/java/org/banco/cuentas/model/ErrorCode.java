package org.banco.cuentas.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCOUNT_NOT_FOUND("A001", "Cuenta no encontrada"),
    ACCOUNT_ALREADY_EXISTS("A002", "La cuenta ya existe"),
    DATABASE_ERROR("A003", "Error en la base de datos"),
    INSUFFICIENT_BALANCE("A004", "Saldo insuficiente"),
    INVALID_OPERATION("A005", "Operación inválida"),
    OPERATION_NOT_FOUND("A006", "Operacion no Existe");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}