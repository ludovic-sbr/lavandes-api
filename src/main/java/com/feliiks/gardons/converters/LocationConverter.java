package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.LocationRequest;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.models.LocationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationConverter {

    @Autowired
    private ModelMapper mapper;

    public LocationEntity convertToEntity(LocationRequest locationRequest) {
        mapper.typeMap(LocationRequest.class, LocationEntity.class).addMappings(elt -> elt.skip(LocationEntity::setId));

        return mapper.map(locationRequest, LocationEntity.class);
    }

    public LocationEntity convertToEntity(LocationModel location) {
        return mapper.map(location, LocationEntity.class);
    }

    public LocationModel convertToModel(LocationEntity location) {
        return mapper.map(location, LocationModel.class);
    }
}
