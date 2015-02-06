package com.packtpub.wflydevelopment.chapter12.control;

import javax.ejb.Stateless;

import com.packtpub.wflydevelopment.chapter12.entity.SeatType;

@Stateless
public class SeatTypeDao extends AbstractDao<SeatType> {

    public SeatTypeDao() {
        super(SeatType.class);
    }
}
