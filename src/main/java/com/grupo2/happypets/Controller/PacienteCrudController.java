package com.grupo2.happypets.Controller;


import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteCrudController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteCrudController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Muestra la lista de todos los pacientes con opción de búsqueda
     */
    @GetMapping
    public String listarPacientes(Model model,
                                  @RequestParam(value = "search", required = false) String searchTerm) {
        List<Paciente> pacientes;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            pacientes = pacienteService.buscarPacientes(searchTerm);
            model.addAttribute("searchTerm", searchTerm);
        } else {
            pacientes = pacienteService.obtenerTodosPacientes();
        }

        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista";
    }

    /**
     * Muestra el formulario para crear un nuevo paciente
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/formulario";
    }

    /**
     * Procesa el formulario para guardar un nuevo paciente
     */
    @PostMapping("/guardar")
    public String guardarPaciente(@Valid @ModelAttribute("paciente") Paciente paciente,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "pacientes/formulario";
        }

        // Validar que el DNI sea único
        if (pacienteService.existePacienteConDni(paciente.getDni(), paciente.getIdPaciente())) {
            result.rejectValue("dni", "error.paciente", "Ya existe un paciente con este DNI");
            return "pacientes/formulario";
        }

        pacienteService.guardarPaciente(paciente);

        String mensaje = paciente.getIdPaciente() != null ?
                "Paciente actualizado correctamente" :
                "Paciente registrado correctamente";

        redirectAttributes.addFlashAttribute("success", mensaje);
        return "redirect:/pacientes";
    }

    /**
     * Muestra el formulario para editar un paciente existente
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        model.addAttribute("paciente", paciente);
        return "pacientes/formulario";
    }

    /**
     * Elimina un paciente por su ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            pacienteService.eliminarPaciente(id);
            redirectAttributes.addFlashAttribute("success", "Paciente eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al eliminar paciente: " + e.getMessage());
        }
        return "redirect:/pacientes";
    }

    /**
     * Muestra los detalles completos de un paciente
     */
    @GetMapping("/detalles/{id}")
    public String verDetallesPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        model.addAttribute("paciente", paciente);
        return "pacientes/detalles";
    }
}