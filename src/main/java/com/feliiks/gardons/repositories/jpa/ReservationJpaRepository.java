package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.sqlmodels.LocationModel;
import com.feliiks.gardons.sqlmodels.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface ReservationJpaRepository extends JpaRepository<ReservationModel, Long> {
    @Query("select reservation from ReservationModel reservation where reservation.reservation_key = :reservationKey")
    Optional<ReservationModel> findByReservationKey(@Param("reservationKey") String reservationKey);

    @Query("select reservation from ReservationModel reservation where reservation.location = :location")
    List<ReservationModel> findByLocation(@Param("location") LocationModel location);

    void deleteById(Long id);
}
