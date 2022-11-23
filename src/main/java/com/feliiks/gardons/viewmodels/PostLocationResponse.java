package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

public class PostLocationResponse {
    private Location registeredLocation;

    public PostLocationResponse(Location registeredLocation) {
        this.registeredLocation = registeredLocation;
    }

    public Location getRegisteredLocation() {
        return registeredLocation;
    }

    public void setRegisteredLocation(Location registeredLocation) {
        this.registeredLocation = registeredLocation;
    }
}
