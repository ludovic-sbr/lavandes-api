package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        Optional<Reservation> reservation;

        try {
            reservation = reservationRepository.findById(id);
            if (reservation.isEmpty()) throw new ResponseStatusException(NOT_FOUND);
        } catch (ResponseStatusException err) {
            String errorMessage = String.format("Une erreur est survenue lors de la récupération de la réservation '%s'.", id);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }

        return reservation;
    }
}