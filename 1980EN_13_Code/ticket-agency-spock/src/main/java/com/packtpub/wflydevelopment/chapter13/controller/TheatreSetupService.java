package com.packtpub.wflydevelopment.chapter13.controller;

import com.packtpub.wflydevelopment.chapter13.control.TicketService;
import com.packtpub.wflydevelopment.chapter13.entity.SeatPosition;
import com.packtpub.wflydevelopment.chapter13.entity.SeatType;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Model
public class TheatreSetupService {

    @Inject
    private FacesContext facesContext;

    @Inject
    private TicketService ticketService;

    @Inject
    private List<SeatType> seatTypes;

    @Produces
    @Named
    private SeatType newSeatType;

    @PostConstruct
    public void initNewSeatType() {
        newSeatType = new SeatType();
    }

    public String createTheatre() {
        ticketService.createTheatre(seatTypes);
        return "book";
    }

    public String restart() {
        ticketService.doCleanUp();
        return "/index";
    }

    public void addNewSeats() throws Exception {
        try {
            ticketService.createSeatType(newSeatType);

            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Seats Added");
            facesContext.addMessage(null, m);
            initNewSeatType();
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

    public List<SeatPosition> getPositions() {
        return Arrays.asList(SeatPosition.values());
    }

}
