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

    /**
     * Obtiene todas las citas registradas
     * @return Lista de citas
     */
    public List<Cita> obtenerTodasCitas() {
        return citaRepository.findAll();
    }

    /**
     * Guarda o actualiza una cita
     * @param cita Entidad Cita a guardar
     */
    public void guardarCita(Cita cita) {
        citaRepository.save(cita);
    }

    /**
     * Obtiene una cita por su ID
     * @param id ID de la cita
     * @return Entidad Cita encontrada
     * @throws RuntimeException si no se encuentra la cita
     */
    public Cita obtenerCitaPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }

    /**
     * Elimina una cita por su ID
     * @param id ID de la cita a eliminar
     * @throws RuntimeException si no se encuentra la cita
     */
    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }

    /**
     * Genera un ticket para una cita específica
     * @param id ID de la cita
     * @return Ticket generado
     */
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
                cita.getPaciente().getDni(),
                cita.getPaciente().getNombre(),
                cita.getPaciente().getApellido(),
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

    /**
     * Métodos adicionales para el dashboard
     */
    public long countCitasBetween(LocalDateTime start, LocalDateTime end) {
        return citaRepository.countByFechaHoraBetween(start, end);
    }

    public List<Cita> findCitasBetween(LocalDateTime start, LocalDateTime end) {
        return citaRepository.findByFechaHoraBetweenOrderByFechaHoraAsc(start, end);
    }

    /**
     * Obtiene citas por DNI de paciente
     * @param dniPaciente DNI del paciente
     * @return Lista de citas del paciente
     */
    public List<Cita> obtenerCitasPorPaciente(String dniPaciente) {
        return citaRepository.findByPacienteDni(dniPaciente);
    }

    /**
     * Obtiene citas por DNI de médico
     * @param dniMedico DNI del médico
     * @return Lista de citas del médico
     */
    public List<Cita> obtenerCitasPorMedico(String dniMedico) {
        return citaRepository.findByMedicoDni(dniMedico);
    }
}