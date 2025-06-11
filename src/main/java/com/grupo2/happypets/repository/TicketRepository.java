package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByCodigoTicket(String codigoTicket);
    Ticket findByCitaIdCita(Long idCita);
}