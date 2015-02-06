package com.packtpub.wflydevelopment.chapter12.batching;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

import com.packtpub.wflydevelopment.chapter12.control.SeatDao;
import com.packtpub.wflydevelopment.chapter12.entity.Seat;

@Named
public class SeatProcessor implements ItemProcessor {

    @Inject
    private SeatDao seatDao;

    @Override
    public Object processItem(Object id) throws Exception {
        final Seat seat = seatDao.find(Long.parseLong((String) id));
        if (seat != null) {
            if (seat.getBooked()) {
                return null;
            }
            seat.setBooked(true);
        }
        return seat;
    }
}
