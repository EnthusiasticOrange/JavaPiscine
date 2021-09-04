package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("messageRepository")
public class MessageRepositoryImpl implements MessageRepository {
    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setOwner(rs.getString("owner"));
            message.setText(rs.getString("text"));
            message.setTime(rs.getTimestamp("time").toLocalDateTime());
            return message;
        }
    }

    private JdbcTemplate jdbcTemplate;
    private MessageRowMapper messageRowMapper = new MessageRowMapper();

    @Autowired
    public MessageRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Message> findById(Long id) {
        String sql = "SELECT * FROM messages WHERE id = ?;";

        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql,
                    messageRowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM messages;";

        return this.jdbcTemplate.query(sql, messageRowMapper);
    }

    @Override
    public Long save(Message entity) {
        String sql = "INSERT INTO messages(owner, text, time) VALUES(?, ?, ?) RETURNING id;";

        return this.jdbcTemplate.queryForObject(sql, Long.class,
                entity.getOwner(), entity.getText(), entity.getTime());
    }

    @Override
    public void update(Message entity) {
        String sql = "UPDATE mesages SET owner = ?, text = ?, time = ? WHERE id = ?;";

        this.jdbcTemplate.update(sql,
                entity.getOwner(), entity.getText(), entity.getTime(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM messages WHERE id = ?;";

        this.jdbcTemplate.update(sql, id);
    }
}
