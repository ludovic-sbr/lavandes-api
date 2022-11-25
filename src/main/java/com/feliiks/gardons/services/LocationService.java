package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.viewmodels.PatchLocationRequest;
import com.feliiks.gardons.viewmodels.PostLocationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<Location> findAll();

    Optional<Location> findById(Long id);

    Optional<Location> findBySlotNumber(int slotNumber);

    Location create(PostLocationRequest postLocationRequest) throws BusinessException;

    Location editLocation(Long id, PatchLocationRequest patchLocationRequest) throws BusinessException;

    Optional<Location> deleteById(Long id);
}
