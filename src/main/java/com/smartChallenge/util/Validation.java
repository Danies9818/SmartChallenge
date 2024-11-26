package com.smartChallenge.util;

import com.smartChallenge.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validation {

    @Value("${regex.email}")
    private  String emailRegex ;

    @Value("${regex.password}")
    private  String passwordRegex;

    public  void validateUserRequest(String email, String password) {
        if (!Pattern.matches(emailRegex, email)) {
            throw new ValidationException("El formato del correo es inválido");
        }

        if (!Pattern.matches(passwordRegex, password)) {
            throw new ValidationException("El formato de la contraseña es inválido: La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un carácter especial (@#$%^&+=).");
        }
    }
}
