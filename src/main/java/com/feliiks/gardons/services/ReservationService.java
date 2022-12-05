package com.feliiks.gardons.services;

import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.viewmodels.LocationEntity;
import com.feliiks.gardons.viewmodels.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReservationService {
    List<ReservationEntity> findAll();

    Optional<ReservationEntity> findById(Long id);

    Optional<ReservationEntity> findByReservationKey(String reservationKey);

    List<ReservationEntity> findByLocation(LocationEntity location);

    ReservationEntity create(ReservationEntity reservation) throws BusinessException;

    ReservationEntity editReservation(Long id, ReservationEntity reservation) throws BusinessException;

    Optional<ReservationEntity> deleteById(Long id);
}
