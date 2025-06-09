package com.grupo2.happypets.Controller;

import com.grupo2.happypets.model.Especialidad;
import com.grupo2.happypets.model.ReservaCita;
import com.grupo2.happypets.model.Servicio;
import com.grupo2.happypets.repository.EspecialidadRepository;
import com.grupo2.happypets.repository.ServicioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final List<ReservaCita> reservas = new ArrayList<>();

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    private String indice(Model model) {
        List<Servicio> servicios = servicioRepository.findAll();
        model.addAttribute("servicios", servicios);
        model.addAttribute("reserva", new ReservaCita());
        model.addAttribute("reservas", reservas);
        return "index";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(@ModelAttribute ReservaCita reserva, Model model) {
        reservas.add(reserva);

        // Generar reporte
        try {
            String filePath = "src/main/resources/reports/reservas.txt";
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file, true);
            writer.write("Nombre: " + reserva.getNombre() + "\n");
            writer.write("Email: " + reserva.getEmail() + "\n");
            writer.write("Teléfono: " + reserva.getTelefono() + "\n");
            writer.write("Servicio: " + reserva.getServicio() + "\n");
            writer.write("Fecha: " + reserva.getFecha() + "\n");
            writer.write("Hora: " + reserva.getHora() + "\n");
            writer.write("=====================================\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Servicio> servicios = servicioRepository.findAll();
        model.addAttribute("servicios", servicios);
        model.addAttribute("reserva", new ReservaCita());
        model.addAttribute("reservas", reservas);
        return "index";
    }

    @GetMapping("/descargarReservas")
    public ResponseEntity<FileSystemResource> descargarReservas() {
        File file = new File("src/main/resources/reports/reservas.txt");
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        FileSystemResource resource = new FileSystemResource(file);
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservas.txt");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/especialidades")
    private String especialidades(Model model) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        return "especialidades";
    }

    @GetMapping("/nosotros")
    private String nosotros(){
        return "nosotros";
    }

    @GetMapping("/privacidad")
    private String privacidad(){
        return "privacidad";
    }

    @GetMapping("/terminos")
    private String terminos(){
        return "terminos";
    }

    @GetMapping("/contactenos")
    private String contactenos(){
        return "contactenos";
    }
}