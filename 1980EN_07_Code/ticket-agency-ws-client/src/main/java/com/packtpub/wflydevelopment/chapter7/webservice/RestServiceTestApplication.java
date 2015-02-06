package com.packtpub.wflydevelopment.chapter7.webservice;

import java.util.Collection;

import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.packtpub.wflydevelopment.chapter7.boundary.AccountDto;
import com.packtpub.wflydevelopment.chapter7.boundary.SeatDto;

public class RestServiceTestApplication {
    private static final String APPLICATION_URL = "http://localhost:8080/ticket-agency-ws/rest/";

    private final WebTarget accountResource;
    private final WebTarget seatResource;

    public static void main(String[] args) {
        new RestServiceTestApplication().runSample();
    }

    public RestServiceTestApplication() {
        final Client restclient = ClientBuilder.newClient();

        accountResource = restclient.target(APPLICATION_URL + "account");
        seatResource = restclient.target(APPLICATION_URL + "seat");
    }

    public void runSample() {

        printAccountStatusFromServer();

        System.out.println("=== Current status: ");
        final Collection<SeatDto> seats = getSeatsFromServer();
        printSeats(seats);

        System.out.println("=== Booking: ");
        bookSeats(seats);

        System.out.println("=== Status after booking: ");
        final Collection<SeatDto> bookedSeats = getSeatsFromServer();
        printSeats(bookedSeats);

        printAccountStatusFromServer();

    }

    private void printAccountStatusFromServer() {
        final AccountDto account = accountResource.request().get(AccountDto.class);
        System.out.println(account);
    }

    private Collection<SeatDto> getSeatsFromServer() {
        return seatResource.request().get(new GenericType<Collection<SeatDto>>() {
        });
    }

    private void printSeats(Collection<SeatDto> seats) {
        seats.forEach(System.out::println);
    }

    private void bookSeats(Collection<SeatDto> seats) {
        for (SeatDto seat : seats) {
            try {
                final String idOfSeat = Integer.toString(seat.getId());
                seatResource.path(idOfSeat).request().post(Entity.json(""), String.class);
                System.out.println(seat + " booked");
            } catch (WebApplicationException e) {
                final Response response = e.getResponse();
                StatusType statusInfo = response.getStatusInfo();
                System.out.println(seat + " not booked (" + statusInfo.getReasonPhrase() + "): "
                    + response.readEntity(JsonObject.class).getString("entity"));
            }

        }
    }
}
