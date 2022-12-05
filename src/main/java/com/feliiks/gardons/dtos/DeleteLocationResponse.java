package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.LocationEntity;

public class DeleteLocationResponse {
    private LocationEntity location;

    public DeleteLocationResponse(LocationEntity location) {
        this.location = location;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
