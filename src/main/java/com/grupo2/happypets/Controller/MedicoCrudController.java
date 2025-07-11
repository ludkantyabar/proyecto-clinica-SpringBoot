package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Cita;
import com.grupo2.happypets.model.HistorialMedico;
import com.grupo2.happypets.model.Medico;
import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.repository.CitaRepository;
import com.grupo2.happypets.repository.MedicoRepository;
import com.grupo2.happypets.repository.UsuarioRepository;
import com.grupo2.happypets.service.EspecialidadService;
import com.grupo2.happypets.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/medicos")
public class MedicoCrudController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private CitaRepository citaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private MedicoRepository medicoRepo;

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

    // Método para listar pacientes con cita y mostrar formulario de historial si corresponde
    @GetMapping("/{idMedico}/pacientes-con-cita")
    public String listarPacientesConCita(@PathVariable Long idMedico,
                                         @RequestParam(required = false) Long idUsuario,
                                         Model model) {
        List<Cita> citas = citaRepo.findByMedicoIdMedico(idMedico);
        Set<Usuario> pacientes = citas.stream()
                .map(Cita::getUsuario)
                .collect(Collectors.toSet());
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("idMedico", idMedico);

        if (idUsuario != null) {
            Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
            Medico medico = medicoRepo.findById(idMedico).orElse(null);
            model.addAttribute("usuario", usuario);
            model.addAttribute("medico", medico);
            model.addAttribute("historial", new HistorialMedico());
        }
        return "medicos/pacientes_con_cita";
    }
}