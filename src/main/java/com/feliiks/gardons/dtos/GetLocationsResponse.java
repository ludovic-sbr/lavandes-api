package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.LocationEntity;

import java.util.List;

public class GetLocationsResponse {
    private List<LocationEntity> locations;

    public GetLocationsResponse(List<LocationEntity> locations) {
        this.locations = locations;
    }

    public List<LocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationEntity> locations) {
        this.locations = locations;
    }
}
