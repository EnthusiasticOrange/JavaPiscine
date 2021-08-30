package edu.school21.chat.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> rooms;

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addCreatedRoom(Chatroom room) {
        if (this.createdRooms == null) {
            this.createdRooms = new LinkedList<>();
        }
        this.createdRooms.add(room);
    }

    public void addJoinedRoom(Chatroom room) {
        if (this.rooms == null) {
            this.rooms = new LinkedList<>();
        }
        this.rooms.add(room);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getJoinedRooms() {
        return rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  "{\n" +
                "id=" + id + ",\n" +
                "login=\"" + login + '\"' + ",\n" +
                "password=\"" + password + '\"' + ",\n" +
                "createdRooms=" + createdRooms + ",\n" +
                "rooms=" + rooms +
                "}";
    }
}
