package com.packtpub.wflydevelopment.chapter8.control;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class JSONEncoder implements Encoder.Text<Object> {

    private Gson gson;

    @Override
    public void init(EndpointConfig config) {
        gson = new Gson();
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public String encode(Object object) throws EncodeException {
        return gson.toJson(object);
    }
}
