package edu.school21.chat.models;

import javax.crypto.MacSpi;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User creator;
    private List<Message> messages;

    public Chatroom() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new LinkedList<>();
        }
        this.messages.add(message);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chatroom)) {
            return false;
        }
        Chatroom chatroom = (Chatroom) o;
        return id.equals(chatroom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  "{\n" +
                "id=" + id + ",\n" +
                "name='" + name + '\'' + ",\n" +
                "creator=" + (creator == null ? "null" : creator.toString().replace('\n', ' ')) + ",\n" +
                "messages=" + messages +
                "}";
    }
}
