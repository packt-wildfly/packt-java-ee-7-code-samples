package com.packtpub.wflydevelopment.chapter12.batching;

import java.io.Serializable;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Named
public class ExternalSystemReader extends AbstractItemReader {

    private WebTarget target;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        final Client restclient = ClientBuilder.newClient();
        this.target = restclient.target("http://localhost:8080/ticket-agency-longterm/rest/external");
    }

    @Override
    public Object readItem() throws Exception {
        return target.request().get(String.class);
    }

}
