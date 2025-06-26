package com.grupo2.happypets.config;

import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import com.grupo2.happypets.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) {
        for (TipoRol tipoRol : TipoRol.values()) {
            String nombreRol = "ROLE_" + tipoRol.name();
            if (!rolRepository.existsByNombre(nombreRol)) {
                Rol rol = new Rol();
                rol.setNombre(nombreRol);
                rol.setTipoRol(tipoRol);
                rolRepository.save(rol);
            }
        }
        System.out.println("Roles inicializados: " + rolRepository.count());
    }
}