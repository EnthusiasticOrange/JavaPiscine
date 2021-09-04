package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    private MessageRepository messageRepository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(4);

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepository") UsersRepository usersRepository,
                            @Qualifier("messageRepository") MessageRepository messageRepository) {
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public boolean signUp(String username, String password) {
        String encoded = encoder.encode(password);
        User u = new User(null, username, encoded);
        Optional<User> exists = usersRepository.findByUsername(username);
        if (!exists.isPresent()) {
            Long id = usersRepository.save(u);
            return true;
        }
        System.out.printf("ERROR: Failed to create new user '%s'. Username already exists\n", username);
        return false;
    }

    @Override
    public Long signIn(String username, String password) {
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User u = userOptional.get();
            String encoded = u.getPassword();
            return encoder.matches(password, encoded) ? u.getId() : -1L;
        }
        return -1L;
    }

    @Override
    public void saveMessage(String user, String message) {
        Message msg = new Message(null, user, message, LocalDateTime.now());
        messageRepository.save(msg);
    }
}
