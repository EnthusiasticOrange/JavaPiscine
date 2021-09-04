package edu.school21.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {
    enum State {
        Initial,
        SignUp,
        SignIn,
        Talk,
        Exit
    }
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private State state = State.Initial;
    private Long id = -1L;
    private String username = null;
    private String password = null;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public Long getUserId() {
        return id;
    }

    public void run() {
        try (InputStreamReader stream = new InputStreamReader(socket.getInputStream());
             BufferedReader in = new BufferedReader(stream);) {
            out = new PrintWriter(socket.getOutputStream(), true);

            String input = null;
            String output = null;

            out.println("Hello from Server!");

            while ((input = in.readLine()) != null) {
                if (state == State.Initial) {
                    output = processCommand(input);
                } else if (state == State.SignUp || state == State.SignIn) {
                    output = processSign(input);
                } else if (input.equals("Exit")) {
                    state = State.Exit;
                    output = "You have left the chat.";
                }
                if (output != null) {
                    out.println(output);
                    output = null;
                } else {
                    server.broadcast(username, input);
                }
                if (state == State.Exit) {
                    break;
                }
            }
            server.removeUser(this);
            socket.close();
        } catch (IOException ex) {
            server.removeUser(this);
        }
    }

    public void sendMessage(String message) {
        if (state == State.Talk) {
            out.println(message);
        }
    }

    private String processCommand(String command) {
        switch (command) {
            case "signUp":
                state = State.SignUp;
                return "Enter username:";
            case "signIn":
                state = State.SignIn;
                return "Enter username:";
            case "Exit":
                return "You have left the chat.";
        }
        return "ERROR: Unknown command";
    }

    private String processSign(String input) {
        if (username == null) {
            username = input;
            return "Enter password:";
        } else if (password == null) {
            password = input;
            switch (state) {
                case SignUp:
                    String output = server.signUp(username, password);
                    username = null;
                    password = null;
                    state = State.Initial;
                    return output;
                case SignIn:
                    id = server.signIn(username, password);
                    if (id == -1) {
                        return  "ERROR: Failed to Sign In";
                    } else {
                        state = State.Talk;
                        return  "Start messaging";
                    }
                default:
                    return  "ERROR: Unexpected error";
            }
        } else {
            return  "ERROR: Unexpected error";
        }
    }
}
