package com.packtpub.wflydevelopment.chapter4.controller;

import com.packtpub.wflydevelopment.chapter4.boundary.TheatreBox;
import com.packtpub.wflydevelopment.chapter4.entity.Seat;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.Collection;

@Model
public class Poller {

    @Inject
    private TheatreBox theatreBox;

    public boolean isPollingActive() {
        return areFreeSeatsAvailable();
    }

    private boolean areFreeSeatsAvailable() {
        final Collection<Seat> list = theatreBox.getSeats();

        for (Seat seat : list) {
            if (!seat.isBooked()) {
                return true;
            }
        }
        return false;
    }

}
