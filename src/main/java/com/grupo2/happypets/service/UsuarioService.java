package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import com.grupo2.happypets.repository.UsuarioRepository;
import com.grupo2.happypets.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        if (usuarioRepository.existsByDniAndIdUsuarioNot(usuarioActualizado.getDni(), id)) {
            throw new IllegalArgumentException("El DNI ya está registrado por otro usuario");
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setDni(usuarioActualizado.getDni());
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public boolean existeUsuarioConDni(String dni, Long idUsuario) {
        if (idUsuario == null) {
            return usuarioRepository.existsByDni(dni);
        } else {
            return usuarioRepository.existsByDniAndIdUsuarioNot(dni, idUsuario);
        }
    }

    public List<Usuario> buscarUsuarios(String searchTerm) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDniContaining(searchTerm, searchTerm, searchTerm);
    }

    public Usuario obtenerUsuarioPorDni(String dni) {
        return usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));
    }

    public long countUsuarios() {
        return usuarioRepository.count();
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            Rol rolUsuario = rolRepository.findByTipoRol(TipoRol.USUARIO);
            if (rolUsuario == null) {
                throw new RuntimeException("No existe el rol USUARIO en la base de datos");
            }
            usuario.setRoles(List.of(rolUsuario));
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

}