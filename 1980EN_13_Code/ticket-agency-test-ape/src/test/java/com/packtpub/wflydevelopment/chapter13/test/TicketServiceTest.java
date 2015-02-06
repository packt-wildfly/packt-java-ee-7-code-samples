package com.packtpub.wflydevelopment.chapter13.test;


import com.packtpub.wflydevelopment.chapter13.control.SeatTypeDao;
import com.packtpub.wflydevelopment.chapter13.control.TicketService;
import com.packtpub.wflydevelopment.chapter13.entity.SeatPosition;
import com.packtpub.wflydevelopment.chapter13.entity.SeatType;
import com.packtpub.wflydevelopment.chapter13.util.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.TestCase.assertNotNull;

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

    @PersistenceContext
    EntityManager em;

    @Test
    public void shouldCreateSeatType() throws Exception {
        // given
        final SeatType seatType = new SeatType();
        seatType.setDescription("Balcony");
        seatType.setPrice(11);
        seatType.setQuantity(5);
        seatType.setPosition(SeatPosition.BOX);
        seatType.setQuantity(32);

        // when
        ticketService.createSeatType(seatType);

        // then
        assertNotNull(seatType.getId());
        assertNotNull(em.find(SeatType.class, seatType.getId()));
    }

    @Inject
    SeatTypeDao seatTypeDao;

    @Test
    @UsingDataSet("datasets/seats.yml")
    @ShouldMatchDataSet("datasets/expected-seats.yml")
    public void shouldMakeACleanup() throws Exception {
        // given
        // from annotation

        // when
        ticketService.doCleanUp();

        // then
        // from annotation
    }
}
