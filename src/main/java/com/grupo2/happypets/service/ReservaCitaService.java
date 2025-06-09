package com.grupo2.happypets.service;

import com.grupo2.happypets.model.ReservaCita;
import com.grupo2.happypets.repository.ReservaCitaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaCitaService {
    private final ReservaCitaRepository reservaCitaRepository;

    public ReservaCitaService(ReservaCitaRepository reservaCitaRepository) {
        this.reservaCitaRepository = reservaCitaRepository;
    }

    public List<ReservaCita> findAll() {
        return reservaCitaRepository.findAll();
    }

    public Optional<ReservaCita> findById(Long id) {
        return reservaCitaRepository.findById(id);
    }

    public ReservaCita save(ReservaCita reservaCita) {
        return reservaCitaRepository.save(reservaCita);
    }

    public void deleteById(Long id) {
        reservaCitaRepository.deleteById(id);
    }
}