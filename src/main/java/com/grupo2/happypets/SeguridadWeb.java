package com.grupo2.happypets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SeguridadWeb {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/index.html",
                                "/",
                                "/api/public/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/login",
                                "/registrar"
                        ).permitAll()
                        // Permitir solo GET a médicos, consultorios y especialidades para ADMIN y PACIENTE
                        .requestMatchers(HttpMethod.GET, "/medicos/**", "/consultorios/**", "/especialidades/**")
                        .hasAnyRole("ADMIN", "PACIENTE")
                        // Solo ADMIN puede modificar (POST, PUT, DELETE, etc.)
                        .requestMatchers("/medicos/**", "/consultorios/**", "/especialidades/**")
                        .hasRole("ADMIN")
                        // Solo PACIENTE puede acceder al formulario de citas
                        .requestMatchers("/citas/formulario.html").hasRole("PACIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/citas/formulario.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}