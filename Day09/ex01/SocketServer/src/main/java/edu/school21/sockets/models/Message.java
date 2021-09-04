package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private String owner;
    private String text;
    private LocalDateTime time;

    public Message() {
    }

    public Message(Long id, String owner, String text, LocalDateTime time) {
        this.id = id;
        this.owner = owner;
        this.text = text;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(owner, message.owner)
                && Objects.equals(text, message.text) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, text, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", owner=" + owner +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
