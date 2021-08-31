package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> rooms;

    public User() {
    }

    public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> rooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.rooms = rooms;
    }

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
        this.createdRooms.add(room);
    }

    public void addJoinedRoom(Chatroom room) {
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
        return Objects.equals(id, user.id) && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(createdRooms, user.createdRooms)
                && Objects.equals(rooms, user.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, rooms);
    }

    private static String createRoomListString(List<Chatroom> roomList) {
        StringBuilder builder = new StringBuilder();
        if (roomList != null) {
            builder.append('[');
            for (Chatroom c : roomList) {
                builder.append(String.format("%s (%d), ", c.getName(), c.getId()));
            }
            if (!roomList.isEmpty()) {
                builder.deleteCharAt(builder.length() - 1)
                        .deleteCharAt(builder.length() - 1);
            }
            builder.append(']');
            return builder.toString();
        }
        return null;
    }

    @Override
    public String toString() {
        return  "{" +
                "id=" + id + "," +
                "login=\"" + login + '\"' + "," +
                "password=\"" + password + '\"' + "," +
                "createdRooms=" + createRoomListString(createdRooms) + "," +
                "rooms=" + createRoomListString(rooms) +
                "}";
    }
}
