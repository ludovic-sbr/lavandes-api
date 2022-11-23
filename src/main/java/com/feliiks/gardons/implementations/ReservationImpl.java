package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationImpl implements ReservationService {
    public final ReservationRepository reservationRepository;

    public ReservationImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }
}