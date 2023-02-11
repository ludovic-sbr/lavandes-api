package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<LocationEntity> findAll();

    List<LocationEntity> findAllByPeriod(Date from, Date to);

    Optional<LocationEntity> findById(Long id);

    LocationEntity create(LocationEntity location, MultipartFile image) throws BusinessException;

    LocationEntity editLocation(Long id, LocationEntity location, MultipartFile image) throws BusinessException;

    Optional<LocationEntity> deleteById(Long id);
}
