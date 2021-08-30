package edu.school21.chat.app;

import com.zaxxer.hikari.*;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(source);
        Optional<Message> messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText(null);
            message.setDateTime(null);
            messagesRepository.update(message);
        } else {
            System.out.println("Message was not found");
            return;
        }
        messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            System.out.printf("Message : %s\n", messageOptional.get());
        } else {
            System.out.println("Updated Message was not found");
        }
    }
}
