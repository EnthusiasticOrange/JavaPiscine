package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class User {
    private String firstName;
    private String lastName;
    private Integer height;

    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.height = 0;
    }

    public User(String firstName, String lastName, Integer height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    public int grow(int value) {
        this.height += value;
        return height;
    }

    public String returnString() {
        return "String";
    }

    public void sayHelloTo(String name) {
        System.out.printf("Hello from '%s' to '%s'\n", firstName, name);
    }

    public long sum(long first, long second) {
        return first + second;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("height=" + height)
                .toString();
    }
}
