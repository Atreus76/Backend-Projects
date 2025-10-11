package com.example.real_time_polling.client;

import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.net.URI;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        ClientManager client = ClientManager.createClient();
        try {
            System.out.println("Connecting to ws://localhost:8080/ws/poll...");
            Session session = client.connectToServer(PollClient.class, new URI("ws://localhost:8080/ws/poll"));
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter vote (OptionA, OptionB, or exit): ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) break;
                session.getAsyncRemote().sendText(input);
            }
            session.close();
        } catch (Exception e) {
            System.err.println("Error running client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
