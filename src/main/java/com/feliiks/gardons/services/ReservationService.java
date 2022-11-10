package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReservationService {
    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
}
