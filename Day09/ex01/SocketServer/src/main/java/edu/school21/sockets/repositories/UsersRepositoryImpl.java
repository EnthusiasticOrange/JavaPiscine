package edu.school21.sockets.repositories;

import javax.sql.DataSource;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("usersRepository")
public class UsersRepositoryImpl implements UsersRepository {
    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    private JdbcTemplate jdbcTemplate;
    private UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    public UsersRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?;";

        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql,
                    userRowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users;";

        return this.jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Long save(User entity) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?) RETURNING id;";

        return this.jdbcTemplate.queryForObject(sql, Long.class, entity.getUsername(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?;";

        this.jdbcTemplate.update(sql, entity.getUsername(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?;";

        this.jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?;";

        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql,
                    userRowMapper, username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
