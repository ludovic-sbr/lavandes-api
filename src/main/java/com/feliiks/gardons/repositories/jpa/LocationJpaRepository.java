package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.models.LocationModel;
import com.feliiks.gardons.models.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "rest")
public interface LocationJpaRepository extends JpaRepository<LocationModel, Long> {
    void deleteById(Long id);

    @Query("select reservation from ReservationModel reservation where reservation.location.id = :id")
    List<ReservationModel> findLocationReservations(Long id);
}
