package com.example.real_time_polling.server;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerApp {
    public static void main(String[] args) {
        // Host = localhost, Port = 8080, Root path = "", Endpoint = PollServer.class
        Server server = new Server("localhost", 8080, "/poll", null, PollServer.class);

        try {
            server.start(); // 🚀 Start WebSocket server
            System.out.println("✅ WebSocket server started at ws://localhost:8080/poll");
            System.out.println("Press ENTER to stop the server...");

            // Keep running until keypress
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            server.stop(); // 🛑 Clean shutdown
            System.out.println("❌ Server stopped.");
        }
    }
}
