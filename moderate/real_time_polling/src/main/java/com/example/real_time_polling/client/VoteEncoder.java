package com.example.real_time_polling.client;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class VoteEncoder implements Encoder.Text<String> {

    // ✅ Converts a Java String vote into a message to send over the WebSocket
    @Override
    public String encode(String vote) {
        // Since it's already a string, just return it as is
        return vote;
    }

    // 🧩 Called once when the encoder is initialized (you can leave it empty)
    @Override
    public void init(EndpointConfig config) {
        // No setup needed
    }

    // 🧹 Called when the encoder is destroyed (you can leave it empty)
    @Override
    public void destroy() {
        // No cleanup needed
    }
}