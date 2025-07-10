package com.grupo2.happypets.controller;

import com.grupo2.happypets.model.HistorialMedico;
import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.model.Medico;
import com.grupo2.happypets.repository.HistorialMedicoRepository;
import com.grupo2.happypets.repository.UsuarioRepository;
import com.grupo2.happypets.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/historiales")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoRepository historialRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private MedicoRepository medicoRepo;

    // Formulario para que el médico llene el historial
    @GetMapping("/nuevo/{idUsuario}")
    public String mostrarFormulario(@PathVariable Long idUsuario, Model model) {
        model.addAttribute("historial", new HistorialMedico());
        model.addAttribute("usuario", usuarioRepo.findById(idUsuario).orElse(null));
        // Simulación: obtener el médico autenticado (ajusta según tu lógica de autenticación)
        Long idMedico = 1L; // Reemplaza por el id real del médico autenticado
        Medico medico = medicoRepo.findById(idMedico).orElse(null);
        model.addAttribute("medico", medico);
        return "historial_form";
    }

    // Guardar historial médico y redirigir a la vista de historiales del usuario
    @PostMapping("/guardar")
    public String guardarHistorial(@ModelAttribute HistorialMedico historial,
                                   @RequestParam Long idUsuario,
                                   @RequestParam Long idMedico) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        Medico medico = medicoRepo.findById(idMedico).orElse(null);
        historial.setUsuario(usuario);
        historial.setMedico(medico);
        historial.setFechaRegistro(LocalDateTime.now());
        historialRepo.save(historial);
        // Redirige a la vista de historiales del paciente
        return "redirect:/historiales/ver?idUsuario=" + idUsuario;
    }

    // Ver historial médico del paciente
    @GetMapping("/ver")
    public String verHistoriales(@RequestParam Long idUsuario, Model model) {
        List<HistorialMedico> historiales = historialRepo.findByUsuarioIdUsuario(idUsuario);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        model.addAttribute("historiales", historiales);
        model.addAttribute("usuario", usuario);
        return "ver_historiales";
    }
}