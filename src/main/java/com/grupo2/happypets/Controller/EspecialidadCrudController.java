package com.grupo2.happypets.Controller;


import com.grupo2.happypets.model.Especialidad;
import com.grupo2.happypets.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadCrudController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public String listarEspecialidades(Model model) {
        model.addAttribute("especialidades", especialidadService.obtenerTodasEspecialidades());
        return "especialidades/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoEspecialidad(Model model) {
        Especialidad especialidad = new Especialidad();
        model.addAttribute("especialidad", especialidad);
        return "especialidades/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEspecialidad(@ModelAttribute("especialidad") Especialidad especialidad) {
        especialidadService.guardarEspecialidad(especialidad);
        return "redirect:/especialidades";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarEspecialidad(@PathVariable Long id, Model model) {
        model.addAttribute("especialidad", especialidadService.obtenerEspecialidadPorId(id));
        return "especialidades/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEspecialidad(@PathVariable Long id) {
        especialidadService.eliminarEspecialidad(id);
        return "redirect:/especialidades";
    }
}

