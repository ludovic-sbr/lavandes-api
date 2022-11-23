package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

public class DeleteLocationResponse {
    private Location deletedLocation;

    public DeleteLocationResponse(Location deletedLocation) {
        this.deletedLocation = deletedLocation;
    }

    public Location getDeletedLocation() {
        return deletedLocation;
    }

    public void setDeletedLocation(Location deletedLocation) {
        this.deletedLocation = deletedLocation;
    }
}
