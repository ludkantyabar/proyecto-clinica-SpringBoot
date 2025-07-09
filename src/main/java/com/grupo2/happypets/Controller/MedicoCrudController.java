package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Medico;
import com.grupo2.happypets.service.EspecialidadService;
import com.grupo2.happypets.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicos")
public class MedicoCrudController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public String listarMedicos(Model model) {
        model.addAttribute("medicos", medicoService.obtenerTodosMedicos());
        return "medicos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMedico(Model model) {
        Medico medico = new Medico();
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.obtenerTodasEspecialidades());
        return "medicos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMedico(@ModelAttribute("medico") Medico medico) {
        medicoService.guardarMedico(medico);
        return "redirect:/medicos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMedico(@PathVariable Long id, Model model) {
        model.addAttribute("medico", medicoService.obtenerMedicoPorId(id));
        model.addAttribute("especialidades", especialidadService.obtenerTodasEspecialidades());
        return "medicos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedico(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
        return "redirect:/medicos";
    }

    @GetMapping("/formulario.html")
    public String medicoFormulario(Model model) {
        model.addAttribute("medico", new Medico());
        model.addAttribute("especialidades", especialidadService.obtenerTodasEspecialidades());
        return "medicos/formulario";
    }
}