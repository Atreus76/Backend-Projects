package com.example.real_time_polling.server;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/poll", encoders = VoteTallyEncoder.class, decoders = VoteDecoder.class)
public class PollServer {
    private static final Map<String, Integer> votes = Collections.synchronizedMap(new HashMap<>());
    private static final Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String vote, Session session) {
        System.out.println("Received vote: " + vote);
        synchronized (votes) {
            votes.put(vote, votes.getOrDefault(vote, 0) + 1);
            VoteTally tally = new VoteTally(votes.getOrDefault("OptionA", 0), votes.getOrDefault("OptionB", 0));
            for (Session client : clients) {
                client.getAsyncRemote().sendObject(tally);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }
}
