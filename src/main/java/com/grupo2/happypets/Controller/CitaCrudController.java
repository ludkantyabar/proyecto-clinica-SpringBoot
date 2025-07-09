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
    private UsuarioService usuarioService;

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
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        List<Consultorio> consultorios = consultorioService.obtenerTodosConsultorios();

        model.addAttribute("cita", cita);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("medicos", medicos);
        model.addAttribute("consultorios", consultorios);

        return "citas/formulario";
    }

    // Mostrar formulario tras login
    @GetMapping("/formulario.html")
    public String mostrarFormularioCita(Model model) {
        Cita cita = new Cita();
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        List<Consultorio> consultorios = consultorioService.obtenerTodosConsultorios();

        model.addAttribute("cita", cita);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("medicos", medicos);
        model.addAttribute("consultorios", consultorios);

        return "citas/formulario";
    }

    // Guardar nueva cita y redirigir al ticket, mostrando error si la validación falla
    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute("cita") Cita cita, Model model) {
        try {
            citaService.asignarHoraYCita(cita); // Usa cita.getFecha()
            citaService.guardarCita(cita);      // Guarda la cita con fecha y hora asignadas
            return "redirect:/citas";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("usuarios", usuarioService.obtenerTodosUsuarios());
            model.addAttribute("medicos", medicoService.obtenerTodosMedicos());
            model.addAttribute("consultorios", consultorioService.obtenerTodosConsultorios());
            model.addAttribute("cita", cita);
            return "citas/formulario";
        }
    }

    // Mostrar formulario para editar cita
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCita(@PathVariable Long id, Model model) {
        Cita cita = citaService.obtenerCitaPorId(id);
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        List<Consultorio> consultorios = consultorioService.obtenerTodosConsultorios();

        model.addAttribute("cita", cita);
        model.addAttribute("usuarios", usuarios);
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
    @GetMapping("/ver-ticket/{id}")
    public String verTicket(@PathVariable Long id, Model model) {
        Ticket ticket = citaService.obtenerTicketPorCitaId(id);
        if (ticket == null) {
            model.addAttribute("error", "No existe ticket para esta cita.");
            return "redirect:/citas";
        }
        model.addAttribute("ticket", ticket);
        return "citas/ticket";
    }

}