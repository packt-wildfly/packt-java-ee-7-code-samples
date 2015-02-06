package com.packtpub.wflydevelopment.chapter11.boundary;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.packtpub.wflydevelopment.chapter11.control.TheatreBox;
import com.packtpub.wflydevelopment.chapter11.entity.Seat;
import org.jboss.logging.Logger;

@Stateless
@Remote(TheatreInfoRemote.class)
public class TheatreInfo implements TheatreInfoRemote {

    private static final Logger logger = Logger.getLogger(TheatreBox.class);

    @EJB
    private TheatreBox box;

    @Override
    public String printSeatList() {
        logger.info("Printing");
        final Collection<Seat> seats = box.getSeats();
        final StringBuilder sb = new StringBuilder();
        for (Seat seat : seats) {
            sb.append(seat.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
