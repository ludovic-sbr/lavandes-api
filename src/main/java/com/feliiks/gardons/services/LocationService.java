package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<LocationEntity> findAll();

    Optional<LocationEntity> findById(Long id);

    Optional<LocationEntity> findBySlotNumber(int slotNumber);

    LocationEntity create(LocationEntity location) throws BusinessException;

    LocationEntity editLocation(Long id, LocationEntity location) throws BusinessException;

    Optional<LocationEntity> deleteById(Long id);
}
