package edu.school21.chat.models;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Date dateTime;

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

    public void setTimestamp(Date timestamp) {
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

    public Date getTimestamp() {
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
        Calendar.Builder builder = new Calendar.Builder();
        Calendar cal = builder.setInstant(dateTime).build();
        String dateTimeStr = String.format("%d/%d/%d %d:%d",
                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        return  "{\n" +
                "id=" + id + ",\n" +
                "author=" + author.toString().replaceAll("\n","") + ",\n" +
                "room=" + room.toString().replaceAll("\n","") + ",\n" +
                "text=\"" + text + '\"' + ",\n" +
                "dateTime=" + dateTimeStr + "\n" +
                "}";
    }
}
