package com.packtpub.wflydevelopment.chapter4.control;

import com.packtpub.wflydevelopment.chapter4.boundary.TheatreBox;
import com.packtpub.wflydevelopment.chapter4.entity.Seat;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class AutomaticSellerService {

    @Inject
    private Logger logger;

    @Inject
    private TheatreBox theatreBox;

    @Resource
    private TimerService timerService;

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void automaticCustomer() {
        final Seat seat = findFreeSeat();

        if (seat == null) {
            cancelTimers();
            logger.info("Scheduler gone!");
            return; // No more seats
        }

        theatreBox.buyTicket(seat.getId());

        logger.info("Somebody just booked seat number " + seat.getId());
    }

    private Seat findFreeSeat() {
        final Collection<Seat> list = theatreBox.getSeats();
        for (Seat seat : list) {
            if (!seat.isBooked()) {
                return seat;
            }
        }
        return null;
    }

    private void cancelTimers() {
        for (Timer timer : timerService.getTimers()) {
            timer.cancel();
        }
    }
}
