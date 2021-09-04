package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket = new Socket();

    public boolean connect(int port) {
        try {
            socket.connect(new InetSocketAddress(port));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            Thread printerThread = new Thread(() -> {
                String input;
                try {
                    while ((input = in.readLine()) != null) {
                        System.out.println(input);
                        if (input.startsWith("ERROR")) {
                            break;
                        } else if (input.equals("You have left the chat.")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.printf("ERROR: %s\n", e.getMessage());
                }
            });

            Thread readerThread = new Thread(() -> {
                String input;
                try {
                    while ((input = in.readLine()) != null) {
                        System.out.println(input);
                        if (input.startsWith("ERROR")) {
                            break;
                        } else if (input.equals("You have left the chat.")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.printf("ERROR: %s\n", e.getMessage());
                }
            });
            printerThread.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            while (printerThread.isAlive()) {
                if (br.ready()) {
                    out.println(br.readLine());
                }
            }
        } catch (IOException e) {
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {}
    }
}
