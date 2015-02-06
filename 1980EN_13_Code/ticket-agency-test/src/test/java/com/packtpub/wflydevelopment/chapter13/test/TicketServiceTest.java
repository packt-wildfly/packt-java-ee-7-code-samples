package com.packtpub.wflydevelopment.chapter13.test;


import com.packtpub.wflydevelopment.chapter13.control.TicketService;
import com.packtpub.wflydevelopment.chapter13.entity.SeatType;
import com.packtpub.wflydevelopment.chapter13.util.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class TicketServiceTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(SeatType.class.getPackage())
                .addPackage(TicketService.class.getPackage())
                .addPackage(LoggerProducer.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TicketService ticketService;

    @Test
    public void shouldCreateSeatType() throws Exception {
        // given
        final SeatType seatType = new SeatType();
        seatType.setDescription("Balcony");
        seatType.setPrice(11);
        seatType.setQuantity(5);

        // when
        ticketService.createSeatType(seatType);

        // then
        assertNotNull(seatType.getId());
    }
}
