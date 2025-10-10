package com.example.real_time_polling.client;

import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.net.URI;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        try {
            // Create a WebSocket client manager
            ClientManager client = ClientManager.createClient();

            // The URI of the WebSocket server
            String uri = "ws://localhost:8080/poll";
            System.out.println("🔌 Connecting to " + uri + "...");

            // Create an instance of your PollClient endpoint
            PollClient pollClient = new PollClient();

            // Connect to the server
            Session session = client.connectToServer(pollClient, URI.create(uri));

            System.out.println("✅ Connected! Type 'OptionA', 'OptionB', or 'exit' to quit.");
            System.out.println("-----------------------------------------------------------");

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);
            String input;

            // Keep reading votes until user types "exit"
            while (true) {
                System.out.print("🗳️ Enter your vote: ");
                input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("👋 Exiting...");
                    break;
                }

                if (input.equalsIgnoreCase("OptionA") || input.equalsIgnoreCase("OptionB")) {
                    try {
                        // Send the vote to the server
                        session.getBasicRemote().sendObject(input);
                    } catch (Exception e) {
                        System.err.println("⚠️ Failed to send vote: " + e.getMessage());
                    }
                } else {
                    System.out.println("❌ Invalid input. Please type 'OptionA' or 'OptionB'.");
                }
            }

            // Close the WebSocket session
            if (session.isOpen()) {
                session.close();
            }

            System.out.println("✅ Client closed.");

        } catch (Exception e) {
            System.err.println("💥 Error running client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
