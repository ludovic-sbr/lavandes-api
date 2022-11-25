package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

public class PostLocationResponse {
    private Location location;

    public PostLocationResponse(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
