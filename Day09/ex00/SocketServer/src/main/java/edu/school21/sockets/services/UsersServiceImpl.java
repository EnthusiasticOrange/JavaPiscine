package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository repository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(4);

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepository") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean signUp(String username, String password) {
        String encoded = encoder.encode(password);
        User u = new User(null, username, encoded);
        Optional<User> exists = repository.findByUsername(username);
        if (!exists.isPresent()) {
            Long id = repository.save(u);
            return true;
        }
        System.out.printf("ERROR: Failed to create new user '%s'. Username already exists\n", username);
        return false;
    }
}
