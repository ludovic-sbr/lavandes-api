package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select reservation from Reservation reservation where reservation.reservation_key = :reservationKey")
    Optional<Reservation> findByReservationKey(@Param("reservationKey") String reservationKey);

    @Query("select reservation from Reservation reservation where reservation.location = :location")
    List<Reservation> findByLocation(@Param("location") Location location);

    void deleteById(Long id);
}
