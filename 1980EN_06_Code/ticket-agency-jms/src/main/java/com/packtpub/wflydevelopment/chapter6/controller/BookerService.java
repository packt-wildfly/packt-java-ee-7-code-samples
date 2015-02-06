package com.packtpub.wflydevelopment.chapter6.controller;

import com.packtpub.wflydevelopment.chapter6.control.TicketService;
import com.packtpub.wflydevelopment.chapter6.jms.BookingQueueProducer;
import com.packtpub.wflydevelopment.chapter6.jms.Priority;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class BookerService implements Serializable {

    private static final long serialVersionUID = -4121692677L;

    @Inject
    private Logger logger;

    @Inject
    private TicketService ticketService;

    @Inject
    private BookingQueueProducer bookingQueueProducer;

    @Inject
    private FacesContext facesContext;

    private int money;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }

    public void bookSeat(long seatId, int price) {
        logger.info("Booking seat " + seatId);

        if (price > money) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not enough Money!",
                    "Registration failed");
            facesContext.addMessage(null, m);
            bookingQueueProducer.sendMessage("Not enough money", Priority.HIGH);
            return;
        }

        ticketService.bookSeat(seatId);

        final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
        facesContext.addMessage(null, m);
        logger.info("Seat booked.");

        money = money - price;

        bookingQueueProducer.sendMessage("[JMS Message] User registered seat " + seatId, Priority.LOW);
    }

    public int getMoney() {
        return money;
    }
}
