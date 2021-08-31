package edu.school21.chat.app;

import com.zaxxer.hikari.*;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.util.*;

public class Program {
    public static void printMessage(Message msg) {
        String msgStr = msg.toString()
                .replaceAll("=", " = ")
                .replaceAll(",", ",\n\t")
                .replaceAll("\\{", "{\n\t")
                .replaceAll("}", "\n}")
                .replaceAll("\n},", "\n\t},");
        System.out.printf("Message : %s\n\n", msgStr);
    }

    public static void main(String[] args) {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(source);
        Optional<Message> messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            System.out.println("========== Message before UPDATE ==========");
            printMessage(messageOptional.get());

            Message message = messageOptional.get();
            message.getAuthor().setId(4L);
            message.setText(null);
            message.setDateTime(null);
            try {
                messagesRepository.update(message);
            } catch (NotSavedSubEntityException e) {
                System.err.println(e.getMessage());
                return;
            }
        } else {
            System.out.println("Message was not found");
            return;
        }
        messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            System.out.println("========== Message after UPDATE ==========");
            printMessage(messageOptional.get());
        } else {
            System.out.println("Updated Message was not found");
        }
    }
}
