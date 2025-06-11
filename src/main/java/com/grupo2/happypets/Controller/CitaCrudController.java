package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.*;
import com.grupo2.happypets.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaCrudController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private ConsultorioService consultorioService;

    // Listar todas las citas
    @GetMapping
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.obtenerTodasCitas());
        return "citas/lista";
    }

    // Mostrar formulario para nueva cita
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCita(Model model) {
        Cita cita = new Cita();
        List<Paciente> pacientes = pacienteService.obtenerTodosPacientes();
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        List<Consultorio> consultorios = consultorioService.obtenerTodosConsultorios();

        model.addAttribute("cita", cita);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("consultorios", consultorios);

        return "citas/formulario";
    }

    // Guardar nueva cita
    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute("cita") Cita cita) {
        citaService.guardarCita(cita);
        return "redirect:/citas";
    }

    // Mostrar formulario para editar cita
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCita(@PathVariable Long id, Model model) {
        Cita cita = citaService.obtenerCitaPorId(id);
        List<Paciente> pacientes = pacienteService.obtenerTodosPacientes();
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        List<Consultorio> consultorios = consultorioService.obtenerTodosConsultorios();

        model.addAttribute("cita", cita);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("consultorios", consultorios);

        return "citas/formulario";
    }

    // Eliminar cita
    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaService.eliminarCita(id);
        return "redirect:/citas";
    }

    // Generar ticket
    @GetMapping("/ticket/{id}")
    public String generarTicket(@PathVariable Long id, Model model) {
        Ticket ticket = citaService.generarTicket(id);
        model.addAttribute("ticket", ticket);
        return "citas/ticket";
    }

}