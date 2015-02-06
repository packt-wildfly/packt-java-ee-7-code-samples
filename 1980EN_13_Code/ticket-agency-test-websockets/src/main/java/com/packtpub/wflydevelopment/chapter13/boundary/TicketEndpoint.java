package com.packtpub.wflydevelopment.chapter13.boundary;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.packtpub.wflydevelopment.chapter13.control.JSONEncoder;
import com.packtpub.wflydevelopment.chapter13.controller.SessionRegistry;
import com.packtpub.wflydevelopment.chapter13.entity.Seat;

@ServerEndpoint(value = "/tickets", encoders={JSONEncoder.class})
public class TicketEndpoint {

    @Inject
    private SessionRegistry sessionRegistry;

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        sessionRegistry.add(session);
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        sessionRegistry.remove(session);
    }

    public void send(@Observes Seat seat) {
        sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendObject(seat));
    }
}