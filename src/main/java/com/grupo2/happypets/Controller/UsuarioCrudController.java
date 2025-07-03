package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import com.grupo2.happypets.repository.RolRepository;
import com.grupo2.happypets.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioCrudController {

    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioCrudController(UsuarioService usuarioService, RolRepository rolRepository) {
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
    }

    @GetMapping
    public String listarUsuarios(Model model,
                                 @RequestParam(value = "search", required = false) String searchTerm) {
        List<Usuario> usuarios;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            usuarios = usuarioService.buscarUsuarios(searchTerm);
            model.addAttribute("searchTerm", searchTerm);
        } else {
            usuarios = usuarioService.obtenerTodosUsuarios();
        }

        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                 BindingResult result,
                                 @RequestParam("rol") String rolNombre,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "usuarios/formulario";
        }

        if (usuarioService.existeUsuarioConDni(usuario.getDni(), usuario.getIdUsuario())) {
            result.rejectValue("dni", "error.usuario", "Ya existe un usuario con este DNI");
            return "usuarios/formulario";
        }

        Rol rol = rolRepository.findByTipoRol(TipoRol.valueOf(rolNombre));
        usuario.getRoles().clear();
        usuario.getRoles().add(rol);

        usuarioService.guardarUsuario(usuario);

        String mensaje = usuario.getIdUsuario() != null ?
                "Usuario actualizado correctamente" :
                "Usuario registrado correctamente";

        redirectAttributes.addFlashAttribute("success", mensaje);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/detalles/{id}")
    public String verDetallesUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/detalles";
    }
}