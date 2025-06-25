package com.grupo2.happypets.seguridad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {
    public static void main(String[] args) {
        String rawPassword = "tu_contraseña"; // Cambia esto por la contraseña a cifrar
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Contraseña cifrada: " + encodedPassword);
    }
}
