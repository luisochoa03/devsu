package org.banco.service;

import org.banco.model.CustomException;
import org.banco.model.ErrorCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password) {
        if (isPasswordSecure(password)) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_SECURE);
        }
        return passwordEncoder.encode(password);
    }

    public String hashNewPassword(String newPassword, String existingPassword) {
        if (isPasswordSecure(newPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_SECURE);
        }
        if (passwordEncoder.matches(newPassword, existingPassword)) {
            throw new CustomException(ErrorCode.PASSWORDS_ARE_EQUAL);
        }
        return passwordEncoder.encode(newPassword);
    }
    private boolean isPasswordSecure(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return !password.matches(regex);
    }
}