package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.LocationEntity;

public class PatchLocationResponse {
    private LocationEntity location;

    public PatchLocationResponse(LocationEntity location) {
        this.location = location;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
