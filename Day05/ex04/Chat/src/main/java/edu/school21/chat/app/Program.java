package edu.school21.chat.app;

import com.zaxxer.hikari.*;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");

        UsersRepository userRepository = new UsersRepositoryJdbcImpl(source);
        List<User> userList = userRepository.findAll(1, 2);

        for (User u : userList) {
            String userStr = u.toString().replaceAll("=", " = ")
                                            .replaceAll(",", ",\n\t")
                                            .replaceAll("\\{", "{\n\t")
                                            .replaceAll("}", "\n}")
                                            .replaceAll("\\[", "[\n\t ")
                                            .replaceAll("]", "\n\t]");

            System.out.printf("User : %s\n", userStr);
            System.out.println();
        }
    }
}
