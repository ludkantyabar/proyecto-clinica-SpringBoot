package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Consultorio;
import com.grupo2.happypets.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultorios")
public class ConsultorioCrudController {

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping
    public String listarConsultorios(Model model) {
        model.addAttribute("consultorios", consultorioService.obtenerTodosConsultorios());
        return "consultorios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoConsultorio(Model model) {
        Consultorio consultorio = new Consultorio();
        model.addAttribute("consultorio", consultorio);
        return "consultorios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarConsultorio(@ModelAttribute("consultorio") Consultorio consultorio) {
        consultorioService.guardarConsultorio(consultorio);
        return "redirect:/consultorios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarConsultorio(@PathVariable Long id, Model model) {
        model.addAttribute("consultorio", consultorioService.obtenerConsultorioPorId(id));
        return "consultorios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarConsultorio(@PathVariable Long id) {
        consultorioService.eliminarConsultorio(id);
        return "redirect:/consultorios";
    }
}