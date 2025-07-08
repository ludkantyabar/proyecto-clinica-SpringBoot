package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Cita;
import com.grupo2.happypets.model.Ticket;
import com.grupo2.happypets.repository.CitaRepository;
import com.grupo2.happypets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository, TicketRepository ticketRepository) {
        this.citaRepository = citaRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Cita> obtenerTodasCitas() {
        return citaRepository.findAll();
    }

    /**
     * Guarda o actualiza una cita y retorna la entidad guardada
     * @param cita Entidad Cita a guardar
     * @return Cita guardada con ID asignado
     */
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita obtenerCitaPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }

    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }

    public Ticket generarTicket(Long id) {
        Cita cita = obtenerCitaPorId(id);

        String contenidoTicket = String.format(
                "CLÍNICA SALUD\n" +
                        "Ticket de Cita Médica\n" +
                        "----------------------------------------\n" +
                        "DNI: %s\n" +
                        "Nombre: %s %s\n" +
                        "Especialidad: %s\n" +
                        "Médico: Dr. %s %s\n" +
                        "Fecha y Hora: %s\n" +
                        "Consultorio: %s\n" +
                        "----------------------------------------\n" +
                        "Por favor, presentese 15 minutos antes.\n" +
                        "En caso de no asistir, avise con anticipación.\n" +
                        "----------------------------------------",
                cita.getUsuario().getDni(),
                cita.getUsuario().getNombre(),
                cita.getUsuario().getApellido(),
                cita.getMedico().getEspecialidad().getNombre(),
                cita.getMedico().getNombre(),
                cita.getMedico().getApellido(),
                cita.getFechaHora().toString(),
                cita.getConsultorio().getCodigo()
        );

        Ticket ticket = new Ticket();
        ticket.setCita(cita);
        ticket.setCodigoTicket("TKT-" + System.currentTimeMillis());
        ticket.setContenido(contenidoTicket);

        return ticketRepository.save(ticket);
    }

    public long countCitasBetween(LocalDateTime start, LocalDateTime end) {
        return citaRepository.countByFechaHoraBetween(start, end);
    }

    public List<Cita> findCitasBetween(LocalDateTime start, LocalDateTime end) {
        return citaRepository.findByFechaHoraBetweenOrderByFechaHoraAsc(start, end);
    }

    public List<Cita> obtenerCitasPorUsuario(String dniUsuario) {
        return citaRepository.findByUsuarioDni(dniUsuario);
    }

    public List<Cita> obtenerCitasPorMedico(String dniMedico) {
        return citaRepository.findByMedicoDni(dniMedico);
    }
}