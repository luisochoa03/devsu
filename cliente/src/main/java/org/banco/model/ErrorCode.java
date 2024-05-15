package org.banco.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CLIENT_NOT_FOUND("C001", "Cliente no encontrado"),
    CLIENT_ALREADY_EXISTS("C002", "El cliente ya existe"),
    DATABASE_ERROR("C003", "Error en la base de datos"),
    PASSWORD_NOT_SECURE("C004","Por favor, crea una contraseña que tenga al menos 8 caracteres de longitud " +
            "e incluya al menos un dígito, una letra minúscula, una letra mayúscula, un carácter especial (@, #, $, %, ^, &, +, =) " +
            "y no contenga espacios en blanco."),
    PASSWORDS_ARE_EQUAL("C004", "La nueva contraseña no puede ser igual a la contraseña actual");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}