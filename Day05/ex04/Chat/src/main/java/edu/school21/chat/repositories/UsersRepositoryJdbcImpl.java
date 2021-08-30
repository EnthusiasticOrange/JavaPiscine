package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        LinkedList<User> retList = new LinkedList<>();
        LinkedList<Chatroom> roomList = new LinkedList<>();

        try {
            Connection con = dataSource.getConnection();
            con.setSchema("mjuli_chat");
            String queryStr =
                    "WITH paged_users AS (\n" +
                    "    SELECT *\n" +
                    "    FROM users LIMIT ? OFFSET ?\n" +
                    "), participants AS (\n" +
                    "    SELECT u.id AS id, u.login , u.password, c.id AS room_id, c.name, FALSE AS is_owner\n" +
                    "    FROM paged_users AS u\n" +
                    "        INNER JOIN users_chatrooms AS uc\n" +
                    "            ON u.id = uc.user_id\n" +
                    "        INNER JOIN chatrooms AS c\n" +
                    "            ON uc.chatroom_id = c.id\n" +
                    "), owners AS (\n" +
                    "    SELECT u.id AS id, u.login , u.password, c.id AS room_id, c.name, TRUE AS is_owner\n" +
                    "    FROM paged_users AS u, chatrooms AS c\n" +
                    "    WHERE u.id = c.owner_id\n" +
                    "), lonely AS (\n" +
                    "    SELECT u.id AS id, u.login , u.password, -1 AS room_id, NULL, FALSE AS is_owner\n" +
                    "    FROM paged_users AS u\n" +
                    "        LEFT JOIN participants AS p\n" +
                    "            ON u.id = p.id WHERE p.id IS NULL\n" +
                    "), find_all AS (\n" +
                    "    SELECT * FROM participants\n" +
                    "    UNION\n" +
                    "    SELECT * FROM owners\n" +
                    "    UNION\n" +
                    "    SELECT * FROM lonely\n" +
                    ")" +
                    "SELECT * FROM find_all ORDER BY find_all.id, find_all.room_id;";
            PreparedStatement query = con.prepareStatement(queryStr);
            query.setInt(1, size);
            query.setInt(2, size * page);

            ResultSet r = query.executeQuery();
            User u = null;
            while (r.next()) {
                Long id = r.getLong("id");
                if (retList.isEmpty() || !retList.getLast().getId().equals(id)) {
                    u = new User(id,
                            r.getString("login"),
                            r.getString("password"),
                            new ArrayList<>(), new ArrayList<>());
                    retList.add(u);
                }
                Long roomId = r.getLong("room_id");
                if (roomId == -1) {
                    continue;
                }
                Chatroom c = roomList.stream()
                                .filter(room -> room.getId().equals(roomId))
                                .findFirst()
                                .orElse(null);
                if (c == null) {
                    c = new Chatroom(roomId, r.getString("name"), null, null);
                    roomList.add(c);
                }
                boolean isOwner = r.getBoolean("is_owner");
                if (isOwner) {
                    c.setCreator(u);
                    u.addCreatedRoom(c);
                } else {
                    u.addJoinedRoom(c);
                }
            }
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }

        return retList;
    }
}
