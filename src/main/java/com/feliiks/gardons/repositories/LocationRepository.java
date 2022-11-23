package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "rest")
public interface LocationRepository extends JpaRepository<Location, Long> {
    void deleteById(Long id);

    Location save(Location location);
}
