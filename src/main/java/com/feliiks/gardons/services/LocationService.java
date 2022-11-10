package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<Location> findAll();
    Optional<Location> findById(Long id);
}
