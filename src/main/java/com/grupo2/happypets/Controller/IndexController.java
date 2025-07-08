// src/main/java/com/grupo2/happypets/Controller/IndexController.java
package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import com.grupo2.happypets.repository.RolRepository;
import com.grupo2.happypets.service.CitaService;
import com.grupo2.happypets.service.ConsultorioService;
import com.grupo2.happypets.service.MedicoService;
import com.grupo2.happypets.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class IndexController {

    private final CitaService citaService;
    private final UsuarioService usuarioService;
    private final MedicoService medicoService;
    private final ConsultorioService consultorioService;
    private final RolRepository rolRepository;

    @Autowired
    public IndexController(CitaService citaService, UsuarioService usuarioService,
                           MedicoService medicoService, ConsultorioService consultorioService,
                           RolRepository rolRepository) {
        this.citaService = citaService;
        this.usuarioService = usuarioService;
        this.medicoService = medicoService;
        this.consultorioService = consultorioService;
        this.rolRepository = rolRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        model.addAttribute("citasHoy", citaService.countCitasBetween(startOfDay, endOfDay));
        model.addAttribute("totalUsuarios", usuarioService.countUsuarios());
        model.addAttribute("totalMedicos", medicoService.countMedicos());
        model.addAttribute("totalConsultorios", consultorioService.countConsultorios());

        LocalDateTime endOfWeek = endOfDay.plusDays(7);
        model.addAttribute("proximasCitas", citaService.findCitasBetween(startOfDay, endOfWeek));

        return "index";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model, HttpServletRequest request) {
        model.addAttribute("usuario", new Usuario());
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "fragments/registro-form :: formulario";
        }
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   @RequestParam("rol") String rolNombre,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registro";
        }

        Rol rol = rolRepository.findByTipoRol(TipoRol.valueOf(rolNombre));
        usuario.setRoles(List.of(rol));

        usuarioService.registrarUsuario(usuario);

        redirectAttributes.addFlashAttribute("success", "Usuario registrado correctamente");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }
    @GetMapping("/index.html")
    public String indexHtml(Model model) {
        return index(model);
    }
}