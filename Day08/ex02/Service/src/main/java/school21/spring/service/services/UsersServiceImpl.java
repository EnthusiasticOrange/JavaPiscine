package school21.spring.service.services;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository repository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String email) {
        String chars = "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            int randNum = ThreadLocalRandom.current().nextInt(chars.length());
            builder.append(chars.charAt(randNum));
        }

        String pass = builder.toString();
        repository.save(new User(1L, email, pass));

        return pass;
    }
}
