package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {
    private UsersService service;
    private ServerSocket serverSocket;

    @Autowired
    public Server(UsersService service) {
        this.service = service;
    }

    public boolean listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void run() {
        if (serverSocket == null) {
            return;
        }

        while (true) {
            try(Socket client = serverSocket.accept();
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(stream);
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);) {
                String command = in.readLine();
                if (command == null) {
                    break;
                }
                if (!command.equals("signUp")) {
                    out.println("ERROR: Unknown command");
                    break;
                }

                out.println("Enter username:");
                String username = in.readLine();
                if (username == null) {
                    break;
                }
                out.println("Enter password:");
                String password = in.readLine();
                if (password == null) {
                    break;
                }
                if (service.signUp(username, password)) {
                    out.println("Successful!");
                } else {
                    out.println("ERROR: Failed to Sign Up");
                }
                break;
            } catch (IOException e) {
                return;
            }
        }
    }
}
