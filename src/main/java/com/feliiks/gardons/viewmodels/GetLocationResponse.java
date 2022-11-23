package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

public class GetLocationResponse {
    private Location location;

    public GetLocationResponse(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
