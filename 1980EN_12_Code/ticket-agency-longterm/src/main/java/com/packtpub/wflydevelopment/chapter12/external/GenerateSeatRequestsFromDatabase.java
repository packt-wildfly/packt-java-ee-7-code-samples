package com.packtpub.wflydevelopment.chapter12.external;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.packtpub.wflydevelopment.chapter12.control.SeatDao;
import com.packtpub.wflydevelopment.chapter12.entity.Seat;

public class GenerateSeatRequestsFromDatabase implements Callable<List<Integer>> {

    private static final int SEATS_TO_RETURN = 3;

    @Inject
    private SeatDao dao;

    @Inject
    private Logger logger;

    @Override
    public List<Integer> call() throws Exception {
        logger.info("Sleeping...");
        Thread.sleep(5000);
        logger.info("Finished sleeping!");

        final List<Seat> databaseSeats = dao.findAll();
        final List<Integer> freeSeats = databaseSeats.stream().filter(seat -> !seat.getBooked()).limit(SEATS_TO_RETURN)
            .map(seat -> seat.getId().intValue()).collect(Collectors.toList());
        if (freeSeats.isEmpty()) {
            logger.info("No seats to book");
        } else {
            logger.info("Requesting booking for " + freeSeats);
        }
        return freeSeats;
    }
}
