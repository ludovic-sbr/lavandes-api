package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select location from Location location where location.slot_number = :slotNumber")
    Optional<Location> findBySlotNumber(@Param("slotNumber") int slotNumber);

    void deleteById(Long id);

    Location save(Location location);
}
