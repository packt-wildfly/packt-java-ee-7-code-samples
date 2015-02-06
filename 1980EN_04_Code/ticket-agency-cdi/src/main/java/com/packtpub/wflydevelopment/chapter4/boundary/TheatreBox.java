package com.packtpub.wflydevelopment.chapter4.boundary;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.packtpub.wflydevelopment.chapter4.entity.Seat;

@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBox {

    @Inject
    private Logger logger;

    private Map<Integer, Seat> seats;

    @Inject
    private Event<Seat> seatEvent;

    @PostConstruct
    public void setupTheatre() {
        seats = new HashMap<>();
        int id = 0;
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Stalls", 40);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Circle", 20);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Balcony", 10);
            addSeat(seat);
        }
        logger.info("Seat Map constructed.");
    }

    private void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    @Lock(READ)
    public Collection<Seat> getSeats() {
        return Collections.unmodifiableCollection(seats.values());
    }

    @Lock(READ)
    public int getSeatPrice(int seatId) {
        return getSeat(seatId).getPrice();
    }

    @Lock(WRITE)
    public void buyTicket(int seatId) {
        final Seat seat = getSeat(seatId);
        final Seat bookedSeat = seat.getBookedSeat();
        addSeat(bookedSeat);

        seatEvent.fire(bookedSeat);
    }

    @Lock(READ)
    private Seat getSeat(int seatId) {
        final Seat seat = seats.get(seatId);
        return seat;
    }
}
