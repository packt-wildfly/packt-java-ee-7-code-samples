package com.packtpub.wflydevelopment.chapter13.control;

import com.packtpub.wflydevelopment.chapter13.entity.SeatType;

import javax.ejb.Stateless;


@Stateless
public class SeatTypeDao extends AbstractDao<SeatType> {

    public SeatTypeDao() {
        super(SeatType.class);
    }
}
