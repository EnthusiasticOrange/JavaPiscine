package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean authenticate(String login, String password) {
        User u = usersRepository.findByLogin(login);

        if (u.isAuthenticated()) {
            throw new AlreadyAuthenticatedException();
        }

        if (u.getPassword().equals(password)) {
            u.setAuthenticated(true);
            usersRepository.update(u);
            return true;
        }
        return false;
    }
}
