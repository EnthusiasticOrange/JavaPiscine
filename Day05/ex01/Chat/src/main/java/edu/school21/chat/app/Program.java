package edu.school21.chat.app;

import com.zaxxer.hikari.*;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String input = s.nextLine();
        Long id;
        try {
            id = Long.parseLong(input);
        } catch (Exception e) {
            System.err.println("Failed to parse input id");
            return;
        }

        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");

        MessagesRepositoryJdbcImpl msgRepo = new MessagesRepositoryJdbcImpl(source);
        Optional<Message> msg = msgRepo.findById(id);
        if (msg.isPresent()) {
            System.out.printf("Message : %s\n", msg.get().toString());
        } else {
            System.out.printf("Message with id=%d was not found\n", id);
        }
    }
}
