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

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
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
}
