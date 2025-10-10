package com.example.real_time_polling.server;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

import javax.json.Json;
import javax.json.JsonObject;

public class VoteTallyEncoder implements Encoder.Text<VoteTally> {
    @Override
    public String encode(VoteTally voteTally) throws EncodeException {
        JsonObject json = Json.createObjectBuilder()
                .add("optionA", voteTally.getOptionA())
                .add("optionB", voteTally.getOptionB())
                .build();
        return json.toString();
    }
    @Override
    public void init(EndpointConfig config){}

    @Override
    public void destroy(){}
}
