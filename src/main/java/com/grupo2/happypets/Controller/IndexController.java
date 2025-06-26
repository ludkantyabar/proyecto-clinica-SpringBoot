package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.service.CitaService;
import com.grupo2.happypets.service.ConsultorioService;
import com.grupo2.happypets.service.MedicoService;
import com.grupo2.happypets.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class IndexController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final ConsultorioService consultorioService;

    @Autowired
    public IndexController(CitaService citaService, PacienteService pacienteService,
                           MedicoService medicoService, ConsultorioService consultorioService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.consultorioService = consultorioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        model.addAttribute("citasHoy", citaService.countCitasBetween(startOfDay, endOfDay));
        model.addAttribute("totalPacientes", pacienteService.countPacientes());
        model.addAttribute("totalMedicos", medicoService.countMedicos());
        model.addAttribute("totalConsultorios", consultorioService.countConsultorios());

        LocalDateTime endOfWeek = endOfDay.plusDays(7);
        model.addAttribute("proximasCitas", citaService.findCitasBetween(startOfDay, endOfWeek));

        return "index";
    }

    // Mostrar formulario de registro (maneja AJAX y acceso normal)
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model, HttpServletRequest request) {
        model.addAttribute("paciente", new Paciente());
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "fragments/registro-form :: formulario";
        }
        return "registro";
    }

    // Procesar registro
    @PostMapping("/registrar")
    public String registrarPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.registrarPaciente(paciente);
        return "redirect:/";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }
}