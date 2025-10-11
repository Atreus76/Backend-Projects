package com.example.real_time_polling.server;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/ws", null, PollServer.class);
        try {
            server.start();
            System.out.println("Server started at ws://localhost:8080/ws/poll");
            System.in.read(); // Keep server running until keypress
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
