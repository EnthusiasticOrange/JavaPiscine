package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsersServiceImplTest {
    @Test
    void checkAuthSuccessful() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        String login = "user";
        String pass = "password";
        User u = new User(1L, login, pass, false);
        Mockito.when(repository.findByLogin(login))
                .thenReturn(u);

        UsersServiceImpl service = new UsersServiceImpl(repository);
        Assertions.assertTrue(service.authenticate(login, pass));
        Mockito.verify(repository).update(u);
    }

    @Test
    void checkIncorrectLogin() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        String login = "user";
        String pass = "password";
        Mockito.when(repository.findByLogin(login))
                .thenThrow(EntityNotFoundException.class);

        UsersServiceImpl service = new UsersServiceImpl(repository);
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.authenticate(login, pass));
    }

    @Test
    void checkIncorrectPassword() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        String login = "user";
        String pass = "password";
        User u = new User(1L, login, pass + "123", false);
        Mockito.when(repository.findByLogin(login))
                .thenReturn(u);

        UsersServiceImpl service = new UsersServiceImpl(repository);
        Assertions.assertFalse(service.authenticate(login, pass));
    }

    @Test
    void checkAlreadyAuthenticated() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        String login = "user";
        String pass = "password";
        User u = new User(1L, login, pass, true);
        Mockito.when(repository.findByLogin(login))
                .thenReturn(u);

        UsersServiceImpl service = new UsersServiceImpl(repository);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> service.authenticate(login, pass));
    }
}
