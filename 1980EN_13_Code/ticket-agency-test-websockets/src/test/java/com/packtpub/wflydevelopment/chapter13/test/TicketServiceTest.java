package com.packtpub.wflydevelopment.chapter13.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.net.URI;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.RestAssured;

@RunAsClient
@RunWith(Arquillian.class)
public class TicketServiceTest {

    private static final String WEBSOCKET_URL = "ws://localhost:8080/ticket-agency-test-websockets/tickets";
    private static final String SEAT_RESOURCE_URL = "http://localhost:8080/ticket-agency-test-websockets/rest/seat";

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(MavenImporter.class).loadPomFromFile("pom.xml").importBuildOutput()
            .as(WebArchive.class);
    }

    @Test
    public void shouldReceiveMessageOnBooking() throws Exception {
        // given
        final int seatNumber = 4;
        final Deque<JsonObject> messages = new ConcurrentLinkedDeque<>();
        final CountDownLatch messageLatch = new CountDownLatch(1);
        final MessageHandler.Whole<String> handler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                messages.add(Json.createReader(new StringReader(message)).readObject());
                messageLatch.countDown();
            }
        };

        ContainerProvider.getWebSocketContainer().connectToServer(new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig endpointConfig) {
                session.addMessageHandler(handler);
            }
        }, new URI(WEBSOCKET_URL));

        // when
        RestAssured.when().post(SEAT_RESOURCE_URL + "/" + seatNumber).then().statusCode(200);
        messageLatch.await(10, TimeUnit.SECONDS);

        // then
        assertThat(messages.size(), equalTo(1));
        final JsonObject message = messages.poll();
        assertThat(message.getInt("id"), equalTo(seatNumber));
    }
}
