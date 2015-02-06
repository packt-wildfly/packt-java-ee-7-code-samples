package com.packtpub.wflydevelopment.chapter7.controller;

import com.packtpub.wflydevelopment.chapter7.boundary.TheatreBox;
import com.packtpub.wflydevelopment.chapter7.entity.Account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class TheatreBooker implements Serializable {

    @Inject
    private Logger logger;

    @Inject
    private TheatreBox theatreBox;

    private Account currentAccount;

    @PostConstruct
    public void createCustomer() {
        currentAccount = new Account(100);
    }

    public void bookSeat(int seatId) {
        logger.info("Booking seat " + seatId);
        final int seatPrice = theatreBox.getSeatPrice(seatId);

        if (seatPrice > currentAccount.getBalance()) {
            throw new IllegalArgumentException("Not enough money!");
        }

        theatreBox.buyTicket(seatId);
        currentAccount = currentAccount.charge(seatPrice);

        logger.info("Seat booked.");
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

}
