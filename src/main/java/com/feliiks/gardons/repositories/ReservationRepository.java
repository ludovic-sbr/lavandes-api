package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "rest")
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
