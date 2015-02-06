package com.packtpub.wflydevelopment.chapter12.control;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packtpub.wflydevelopment.chapter12.entity.Seat;
import com.packtpub.wflydevelopment.chapter12.entity.SeatPosition;
import com.packtpub.wflydevelopment.chapter12.entity.SeatType;

@Singleton
@Startup
public class DatabaseInitializer {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void setup() {
        final SeatType seatType = new SeatType();
        seatType.setPosition(SeatPosition.BALCONY);
        seatType.setDescription("Test");
        seatType.setQuantity(10);
        seatType.setPrice(10);
        em.persist(seatType);

        final Seat seat = new Seat();
        seat.setSeatType(seatType);
        em.persist(seat);
    }
}
