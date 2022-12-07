package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.PatchLocationRequest;
import com.feliiks.gardons.dtos.PostLocationRequest;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.models.LocationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationConverter {

    @Autowired
    private ModelMapper mapper;

    public LocationEntity convertToEntity(PostLocationRequest postLocationRequest) {
        mapper.typeMap(PostLocationRequest.class, LocationEntity.class).addMappings(elt -> {
            elt.skip(LocationEntity::setId);
            elt.skip(LocationEntity::setAvailable);
        });

        return mapper.map(postLocationRequest, LocationEntity.class);
    }

    public LocationEntity convertToEntity(PatchLocationRequest patchLocationRequest) {
        mapper.typeMap(PatchLocationRequest.class, LocationEntity.class).addMappings(elt -> {
            elt.skip(LocationEntity::setId);
            elt.skip(LocationEntity::setSlot_number);
        });

        return mapper.map(patchLocationRequest, LocationEntity.class);
    }

    public LocationEntity convertToEntity(LocationModel location) {
        return mapper.map(location, LocationEntity.class);
    }

    public LocationModel convertToModel(LocationEntity location) {
        return mapper.map(location, LocationModel.class);
    }
}
