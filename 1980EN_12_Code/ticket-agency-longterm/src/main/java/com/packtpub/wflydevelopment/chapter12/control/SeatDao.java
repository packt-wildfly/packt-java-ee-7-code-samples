package com.packtpub.wflydevelopment.chapter12.control;

import javax.ejb.Stateless;

import com.packtpub.wflydevelopment.chapter12.entity.Seat;

@Stateless
public class SeatDao extends AbstractDao<Seat> {

    public SeatDao() {
        super(Seat.class);
    }
}
