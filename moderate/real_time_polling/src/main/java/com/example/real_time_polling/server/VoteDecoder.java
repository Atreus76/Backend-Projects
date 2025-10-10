package com.example.real_time_polling.server;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class VoteDecoder implements Decoder.Text<String> {
    @Override
    public String decode(String s) throws DecodeException {
        return s.trim();
    }

    @Override
    public boolean willDecode(String s) {
        if (s == null) return false;
        s = s.trim();
        return s.equalsIgnoreCase("OptionA") || s.equalsIgnoreCase("OptionB");
    }

    @Override
    public void init(EndpointConfig config) {}
    @Override
    public void destroy() {}


}
