package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.sqlmodels.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface LocationJpaRepository extends JpaRepository<LocationModel, Long> {
    @Query("select location from LocationModel location where location.slot_number = :slotNumber")
    Optional<LocationModel> findBySlotNumber(@Param("slotNumber") int slotNumber);

    void deleteById(Long id);
}
