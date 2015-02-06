package com.packtpub.wflydevelopment.chapter13.control;

import com.packtpub.wflydevelopment.chapter13.entity.Seat;

import javax.ejb.Stateless;


@Stateless
public class SeatDao extends AbstractDao<Seat> {

    public SeatDao() {
        super(Seat.class);
    }
}
