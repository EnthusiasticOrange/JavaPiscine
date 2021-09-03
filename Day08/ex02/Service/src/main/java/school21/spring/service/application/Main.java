package school21.spring.service.application;

import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void repoExample(UsersRepository repository) {
        System.out.println("Initial state of database ( findAll() )");
        System.out.println(repository.findAll());
        System.out.println();

        User u1 = new User(10L, "john@email.com");
        System.out.printf("Creating new %s\n", u1);
        repository.save(u1);
        System.out.println(repository.findById(u1.getId()));
        System.out.println();

        System.out.println("Updating created User");
        u1.setEmail("new@edu.com");
        repository.update(u1);
        System.out.println("Finding created User by Email");
        System.out.println(repository.findByEmail(u1.getEmail()));
        System.out.println();

        System.out.println("Deleting created User");
        repository.delete(u1.getId());
        System.out.println(repository.findById(u1.getId()));
        System.out.println();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println("========== usersRepositoryJdbc ==========");
        repoExample(usersRepository);

        System.out.println("========== usersRepositoryJdbcTemplate ==========");
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        repoExample(usersRepository);
    }
}
