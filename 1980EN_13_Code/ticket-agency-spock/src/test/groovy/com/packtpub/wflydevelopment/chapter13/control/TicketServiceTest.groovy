package com.packtpub.wflydevelopment.chapter13.control

import com.packtpub.wflydevelopment.chapter13.control.TicketService
import com.packtpub.wflydevelopment.chapter13.entity.SeatType
import com.packtpub.wflydevelopment.chapter13.util.LoggerProducer
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.Archive
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
class TicketServiceTest extends Specification {

    @Deployment
    def static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(SeatType.class.getPackage())
                .addPackage(TicketService.class.getPackage())
                .addPackage(LoggerProducer.class.getPackage())
                .addAsResource('META-INF/persistence.xml')
                .addAsWebInfResource(EmptyAsset.INSTANCE, 'beans.xml');
    }

    @Inject
    TicketService ticketService;


    def "should create SeatType"() {
        given:
        def seatType = new SeatType(description: "Balcony", price: 11, quantity: 6)

        when:
        ticketService.createSeatType(seatType);

        then:
        seatType.getId() != null
    }
}
