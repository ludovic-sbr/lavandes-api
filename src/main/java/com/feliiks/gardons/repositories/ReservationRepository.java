package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.LocationConverter;
import com.feliiks.gardons.converters.ReservationConverter;
import com.feliiks.gardons.repositories.jpa.ReservationJpaRepository;
import com.feliiks.gardons.sqlmodels.LocationModel;
import com.feliiks.gardons.sqlmodels.ReservationModel;
import com.feliiks.gardons.viewmodels.LocationEntity;
import com.feliiks.gardons.viewmodels.ReservationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationConverter reservationConverter;
    private final LocationConverter locationConverter;

    public ReservationRepository(
            ReservationJpaRepository reservationJpaRepository,
            ReservationConverter reservationConverter,
            LocationConverter locationConverter) {
        this.reservationJpaRepository = reservationJpaRepository;
        this.reservationConverter = reservationConverter;
        this.locationConverter = locationConverter;
    }

    public List<ReservationEntity> findAll() {
        List<ReservationModel> allReservations = reservationJpaRepository.findAll();

        return allReservations.stream().map(reservationConverter::convertToEntity).toList();
    }

    public Optional<ReservationEntity> findById(Long id) {
        Optional<ReservationModel> reservation = reservationJpaRepository.findById(id);

        if (reservation.isEmpty()) return Optional.empty();

        return Optional.of(reservationConverter.convertToEntity(reservation.get()));
    }

    public Optional<ReservationEntity> findBySessionId(String sessionId) {
        Optional<ReservationModel> reservation = reservationJpaRepository.findBySessionId(sessionId);

        if (reservation.isEmpty()) return Optional.empty();

        return Optional.of(reservationConverter.convertToEntity(reservation.get()));
    }

    public Optional<ReservationEntity> findByReservationKey(String reservationKey) {
        Optional<ReservationModel> reservation = reservationJpaRepository.findByReservationKey(reservationKey);

        if (reservation.isEmpty()) return Optional.empty();

        return Optional.of(reservationConverter.convertToEntity(reservation.get()));
    }

    public List<ReservationEntity> findByLocation(LocationEntity location) {
        LocationModel mappedLocation = locationConverter.convertToModel(location);
        List<ReservationModel> reservations = reservationJpaRepository.findByLocation(mappedLocation);

        return reservations.stream().map(reservationConverter::convertToEntity).toList();
    }

    public ReservationEntity save(ReservationEntity reservation) {
        ReservationModel mappedReservation = reservationConverter.convertToModel(reservation);

        ReservationModel savedReservation = reservationJpaRepository.save(mappedReservation);

        return reservationConverter.convertToEntity(savedReservation);
    }

    public void deleteById(Long id) {
        reservationJpaRepository.deleteById(id);
    }
}
