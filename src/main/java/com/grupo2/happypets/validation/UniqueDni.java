package com.grupo2.happypets.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueDniValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDni {
    String message() default "El DNI ya está registrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}