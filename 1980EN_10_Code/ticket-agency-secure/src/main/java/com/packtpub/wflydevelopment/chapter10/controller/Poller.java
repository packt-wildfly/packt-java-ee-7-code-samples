package com.packtpub.wflydevelopment.chapter10.controller;

import com.packtpub.wflydevelopment.chapter10.boundary.TheatreBox;
import com.packtpub.wflydevelopment.chapter10.entity.Seat;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@Model
public class Poller {

    @Inject
    private TheatreBox theatreBox;

    public boolean isPollingActive() {
        return areFreeSeatsAvailable();
    }

    private boolean areFreeSeatsAvailable() {
        final Optional<Seat> firstSeat = theatreBox.getSeats().stream().filter(seat -> !seat.isBooked()).findFirst();
        return firstSeat.isPresent();
    }

}
