package com.packtpub.wflydevelopment.chapter13.boundary;

import com.packtpub.wflydevelopment.chapter13.controller.TheatreBooker;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("/seat")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class SeatsResource {

    @Inject
    private TheatreBooker theatreBooker;

    @Inject
    private TheatreBox theatreBox;

    @GET
    public Collection<SeatDto> getSeatList() {
        return theatreBox.getSeats().stream().map(SeatDto::fromSeat).collect(Collectors.toList());
    }

    @POST
    @Path("{id}")
    public Response bookPlace(@PathParam("id") int id) {
        try {
            theatreBooker.bookSeat(id);
            return Response.ok(SeatDto.fromSeat(theatreBox.getSeat(id))).build();
        } catch (Exception e) {
            final Entity<String> errorMessage = Entity.json(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }
}
