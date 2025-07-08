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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SeguridadWeb {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Recursos públicos
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/api/public/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/login",
                                "/registrar"
                        ).permitAll()
                        // Permitir acceso a /citas/** a ADMIN y USUARIO
                        .requestMatchers("/citas/**").hasAnyRole("ADMIN", "USUARIO")
                        .requestMatchers(
                                "/consultorios/**",
                                "/especialidades/**",
                                "/usuarios/**"
                        ).hasRole("ADMIN")
                        .requestMatchers("/medicos/**").hasAnyRole("ADMIN", "MEDICO")
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                if (roles.contains("ROLE_MEDICO")) {
                    response.sendRedirect("/medicos/formulario.html");
                } else if (roles.contains("ROLE_USUARIO")) {
                    response.sendRedirect("/citas/formulario.html");
                } else {
                    response.sendRedirect("/index.html");
                }
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}