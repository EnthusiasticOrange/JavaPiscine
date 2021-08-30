package edu.school21.chat.app;

import com.zaxxer.hikari.*;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.util.*;
import java.time.LocalDateTime;

public class Program {
    public static void trySave(MessagesRepository repo, Message msg) {
        try {
            repo.save(msg);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");

        User creator = new User(null, "RapidGoose", "qwerty", new ArrayList(), new ArrayList());
        User author = creator;
        Chatroom room = new Chatroom(null, "General", creator, new ArrayList());
        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(source);

        System.out.println("========== Tying to add new Message with author id = null ==========");
        trySave(messagesRepository, message);

        System.out.println("========== Tying to add new Message with room id = null ==========");
        creator.setId(10L);
        trySave(messagesRepository, message);

        System.out.println("========== Tying to add new Message with invalid user id ==========");
        room.setId(10L);
        trySave(messagesRepository, message);

        System.out.println("========== Tying to add new Message with invalid room id ==========");
        creator.setId(1L);
        trySave(messagesRepository, message);

        System.out.println("========== Tying to add new Message with valid input ==========");
        room.setId(1L);
        trySave(messagesRepository, message);

        System.out.println(message.getId());
    }
}
