package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    public MessagesRepositoryJdbcImpl(DataSource source) {
        dataSource = source;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            Connection con = dataSource.getConnection();
            con.setSchema("mjuli_chat");
            String queryStr =
                    "SELECT messages.*, users.login AS user_login, users.password AS user_password, chatrooms.name AS chatroom_name\n" +
                    "   FROM messages, users, chatrooms\n" +
                    "   WHERE messages.author_id = users.id AND messages.room_id = chatrooms.id AND messages.id = ?;";
            PreparedStatement query = con.prepareStatement(queryStr);
            query.setLong(1, id);
            ResultSet result = query.executeQuery();
            if (!result.next()) {
                return Optional.empty();
            }

            User user = new User();
            Chatroom room = new Chatroom();
            Message msg = new Message(id, user, room, null, null);
            Timestamp time = result.getTimestamp("time");


            msg.setText(result.getString("text"));
            msg.setDateTime(time == null ? null : time.toLocalDateTime());

            user.setId(result.getLong("author_id"));
            user.setLogin(result.getString("user_login"));
            user.setPassword(result.getString("user_password"));

            room.setId(result.getLong("room_id"));
            room.setName(result.getString("chatroom_name"));

            return Optional.of(msg);
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        User author = message.getAuthor();
        Chatroom room = message.getRoom();
        if (author.getId() == null) {
            throw new NotSavedSubEntityException("Author id is null");
        }
        if (room.getId() == null) {
            throw new NotSavedSubEntityException("Room id is null");
        }

        try {
            Connection con = dataSource.getConnection();
            con.setSchema("mjuli_chat");
            String queryStr = "INSERT INTO messages (author_id, room_id, text, time) VALUES (?, ?, ?, ?) RETURNING id;";
            PreparedStatement query = con.prepareStatement(queryStr);
            query.setLong(1, author.getId());
            query.setLong(2, room.getId());
            query.setString(3, message.getText());
            query.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            ResultSet result = query.executeQuery();
            if(!result.next()) {
                throw new NotSavedSubEntityException("Failed to save message");
            }
            message.setId(result.getLong(1));
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        LocalDateTime loc = message.getDateTime();
        try {
            Connection con = dataSource.getConnection();
            con.setSchema("mjuli_chat");
            String queryStr = "UPDATE messages " +
                    "SET id = ?, author_id = ?, room_id = ?, text = ?, time = ? " +
                    "WHERE messages.id = ?;";
            PreparedStatement query = con.prepareStatement(queryStr);
            query.setLong(1, message.getId());
            query.setLong(2, message.getAuthor().getId());
            query.setLong(3, message.getRoom().getId());
            query.setString(4, message.getText());
            query.setTimestamp(5, loc == null ? null : Timestamp.valueOf(message.getDateTime()));
            query.setLong(6, message.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
    }
}
