package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Location;

public class PatchLocationResponse {
    Location patchedLocation;

    public PatchLocationResponse(Location patchedLocation) {
        this.patchedLocation = patchedLocation;
    }

    public Location getPatchedUser() {
        return patchedLocation;
    }

    public void setPatchedUser(Location patchedLocation) {
        this.patchedLocation = patchedLocation;
    }
}
