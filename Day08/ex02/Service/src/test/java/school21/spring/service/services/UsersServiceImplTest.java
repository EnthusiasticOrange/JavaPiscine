package school21.spring.service.services;

import school21.spring.service.config.TestApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UsersServiceImplTest {
    @Test
    void testSignUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        UsersService service = context.getBean("usersService", UsersService.class);

        Assertions.assertNotNull(service.signUp("user@email.edu"));
    }
}
