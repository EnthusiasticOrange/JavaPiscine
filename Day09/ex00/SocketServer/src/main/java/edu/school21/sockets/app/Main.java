package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);

        Server server = context.getBean("server", Server.class);
        if (server.listen(8080)) {
            System.out.println("Server has started successfully");
            server.run();
        }
        System.out.println("Failed to start Server");
    }
}
