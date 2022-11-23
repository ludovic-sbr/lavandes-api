package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

import java.util.List;

public class GetLocationsResponse {
    private List<Location> locations;

    public GetLocationsResponse(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
