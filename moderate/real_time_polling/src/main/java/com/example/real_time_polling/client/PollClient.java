package com.example.real_time_polling.client;

import jakarta.websocket.*;

import java.io.IOException;

@ClientEndpoint(
        encoders = VoteEncoder.class,
        decoders = VoteTallyDecoder.class
)
public class PollClient {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(VoteTally tally) {
        System.out.println("Received tally: OptionA: " + tally.getOptionA() + ", OptionB: " + tally.getOptionB());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected from server");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Client error: " + throwable.getMessage());
    }
}
