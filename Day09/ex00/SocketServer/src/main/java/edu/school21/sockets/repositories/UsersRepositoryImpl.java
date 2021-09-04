package edu.school21.sockets.repositories;

import javax.sql.DataSource;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("usersRepository")
public class UsersRepositoryImpl implements UsersRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id;";

        Map namedParameters = Collections.singletonMap("id", id);

        try {
            return this.jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users;";

        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users(username, password) VALUES(:username, :password);";

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);

        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET username = :username WHERE id = :id;";

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);

        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = :id;";

        Map namedParameters = Collections.singletonMap("id", id);

        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username;";

        Map namedParameters = Collections.singletonMap("username", username);

        return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(User.class)));
    }
}
