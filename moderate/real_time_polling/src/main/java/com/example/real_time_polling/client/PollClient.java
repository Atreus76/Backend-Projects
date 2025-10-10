package com.example.real_time_polling.client;

import jakarta.websocket.*;

import java.io.IOException;

@ClientEndpoint(
        encoders = {VoteEncoder.class},
        decoders = {VoteTallyDecoder.class}
)
public class PollClient {

    private Session session;

    // ✅ Called when the connection to the server is established
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("✅ Connected to server: " + session.getRequestURI());
    }

    // ✅ Called whenever a VoteTally message arrives from the server
    @OnMessage
    public void onMessage(VoteTally tally) {
        System.out.printf("📊 Current Results → OptionA: %d, OptionB: %d%n",
                tally.getOptionA(), tally.getOptionB());
    }

    // ✅ Called when the connection is closed
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("❌ Disconnected from server: " + reason.getReasonPhrase());
    }

    // ✅ Called when there’s an error during communication
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("💥 WebSocket Error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    // ✅ Optional helper method to send a vote message
    public void sendVote(String option) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendObject(option);
                System.out.println("🗳️ Sent vote: " + option);
            } catch (IOException | EncodeException e) {
                System.err.println("⚠️ Failed to send vote: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Not connected to server!");
        }
    }
}
