package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.LocationEntity;

public class PostLocationResponse {
    private LocationEntity location;

    public PostLocationResponse(LocationEntity location) {
        this.location = location;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
