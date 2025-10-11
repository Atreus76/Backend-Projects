package com.example.real_time_polling.client;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class VoteTallyDecoder implements Decoder.Text<VoteTally> {
    @Override
    public VoteTally decode(String s) throws DecodeException {
        JsonObject json = Json.createReader(new java.io.StringReader(s)).readObject();
        return new VoteTally(json.getInt("optionA"), json.getInt("optionB"));
    }

    @Override
    public boolean willDecode(String s) {
        try {
            Json.createReader(new java.io.StringReader(s)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {}
    @Override
    public void destroy() {}
}
