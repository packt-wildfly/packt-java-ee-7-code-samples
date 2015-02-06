package com.packtpub.wflydevelopment.chapter6.jms;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;

import java.util.logging.Logger;


@MessageDriven(name = "MDBService", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "java:jboss/activemq/queue/TicketQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),}
)
@ResourceAdapter(value="activemq-rar-5.9.0.rar")
public class BookingQueueReceiver implements MessageListener {

    @Inject
    private Logger logger;

    @Override
    public void onMessage(Message message) {
        try {
            final TextMessage tm = (TextMessage) message;
            logger.info("Received message " + tm.getText());
        } catch (JMSException ex) {
            logger.severe(ex.toString());
        }
    }
}
