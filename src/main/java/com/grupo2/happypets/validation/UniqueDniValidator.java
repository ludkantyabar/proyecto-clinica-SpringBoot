package com.grupo2.happypets.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.grupo2.happypets.repository.PacienteRepository;

public class UniqueDniValidator implements ConstraintValidator<UniqueDni, String> {

    private PacienteRepository pacienteRepository;

    @Override
    public void initialize(UniqueDni constraintAnnotation) {
        this.pacienteRepository = SpringContext.getBean(PacienteRepository.class);
    }

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || dni.isEmpty()) {
            return true;
        }
        return !pacienteRepository.existsByDni(dni);
    }
}