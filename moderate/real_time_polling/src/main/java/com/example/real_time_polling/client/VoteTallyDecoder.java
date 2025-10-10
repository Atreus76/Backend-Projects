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
    public VoteTally decode(String jsonMessage) throws DecodeException {
        try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
            JsonObject jsonObject = reader.readObject();

            int optionA = jsonObject.getInt("optionA", 0);
            int optionB = jsonObject.getInt("optionB", 0);

            return new VoteTally(optionA, optionB);
        } catch (Exception e) {
            throw new DecodeException(jsonMessage, "Failed to decode JSON into VoteTally", e);
        }
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
            JsonObject obj = reader.readObject();
            return obj.containsKey("optionA") && obj.containsKey("optionB");
        } catch (Exception e) {
            return false; // Not valid JSON
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
