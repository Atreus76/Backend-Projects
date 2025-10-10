package com.example.real_time_polling.server;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/poll", encoders = {VoteTallyEncoder.class}, decoders = {VoteDecoder.class})
public class PollServer {
    private static final Set<Session> sessions = new HashSet<>();
    private static final HashMap<String, Integer> voteCounts = new HashMap<>();

    static {
        voteCounts.put("OptionA", 0);
        voteCounts.put("OptionB", 0);
    }

    @OnOpen
    public void onOpen(Session session) {
        synchronized (sessions){
            sessions.add(session);
        }
        System.out.println("Client connected: " + session.getId());
        broadcastTally();
    }

    @OnMessage
    public void onMessage(Session session, String vote) throws IOException{
        System.out.println("Received vote: "+ vote + " from " + session.getId());
        synchronized (voteCounts) {
            // increment the count for the voted option
            if (voteCounts.containsKey(vote)) {
                voteCounts.put(vote, voteCounts.get(vote) + 1);
            } else {
                System.out.println("⚠️ Unknown vote option: " + vote);
            }
        }
        // Create a VoteTally object with updated counts
        VoteTally tally = new VoteTally(
                voteCounts.get("OptionA"),
                voteCounts.get("OptionB")
        );

        // Broadcast new tally to all clients
        broadcast(tally);

    }

    @OnClose
    public void onClose(Session session) throws IOException{
        synchronized (sessions) {
            sessions.remove(session);
        }
        System.out.println("❌ Client disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        System.err.println("💥 Error in session " + (session != null ? session.getId() : "unknown") + ": " + throwable);
    }
    // 🛰️ Broadcast a VoteTally object to all connected clients
    private void broadcast(VoteTally tally) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendObject(tally);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 📤 Send the current tally to all clients (for @OnOpen initialization)
    private void broadcastTally() {
        VoteTally tally = new VoteTally(
                voteCounts.get("OptionA"),
                voteCounts.get("OptionB")
        );
        broadcast(tally);
    }
}
