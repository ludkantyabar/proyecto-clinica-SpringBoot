package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCita_IdCita(Long idCita);
}