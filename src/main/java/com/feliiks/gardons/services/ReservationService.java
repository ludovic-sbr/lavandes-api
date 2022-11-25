package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.viewmodels.PatchReservationRequest;
import com.feliiks.gardons.viewmodels.PostReservationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReservationService {
    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    Optional<Reservation> findByReservationKey(String reservationKey);

    List<Reservation> findByLocation(Location location);

    Reservation create(PostReservationRequest postReservationRequest) throws BusinessException;

    Reservation editReservation(Long id, PatchReservationRequest patchReservationRequest) throws BusinessException;

    Optional<Reservation> deleteById(Long id);
}
