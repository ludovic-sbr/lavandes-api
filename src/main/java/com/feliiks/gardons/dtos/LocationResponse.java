package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.LocationEntity;

public class LocationResponse {
    private LocationEntity location;

    public LocationResponse(LocationEntity location) {
        this.location = location;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
