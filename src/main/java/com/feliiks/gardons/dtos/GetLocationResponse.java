package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.LocationEntity;

public class GetLocationResponse {
    private LocationEntity location;

    public GetLocationResponse(LocationEntity location) {
        this.location = location;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
