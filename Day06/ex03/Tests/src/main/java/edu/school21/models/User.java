package edu.school21.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean isAuthenticated;

    public User(Long id, String login, String password, boolean isAuthenticated) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isAuthenticated = isAuthenticated;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
