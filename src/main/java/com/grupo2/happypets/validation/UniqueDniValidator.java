package com.grupo2.happypets.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.grupo2.happypets.repository.UsuarioRepository;

@Component
public class UniqueDniValidator implements ConstraintValidator<UniqueDni, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || dni.isEmpty()) {
            return true; // La validación @NotBlank se encarga de esto
        }
        return !usuarioRepository.existsByDni(dni);
    }
}