package edu.school21.chat.models;

import java.util.Calendar;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.dateTime = timestamp;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return dateTime;
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
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String dateTimeStr = String.format("%d/%d/%d %d:%d",
                dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear(),
                dateTime.getHour(), dateTime.getMinute());
        return  "{\n" +
                "id=" + id + ",\n" +
                "author=" + author.toString().replaceAll("\n","") + ",\n" +
                "room=" + room.toString().replaceAll("\n","") + ",\n" +
                "text=\"" + text + '\"' + ",\n" +
                "dateTime=" + dateTimeStr + "\n" +
                "}";
    }
}
