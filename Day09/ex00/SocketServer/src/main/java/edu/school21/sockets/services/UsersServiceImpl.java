package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository repository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepository") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean signUp(String login, String password) {
        return true;
    }
}
