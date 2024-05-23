package com.abujava.configserver;

/**
 * This class is not documented :(
 *
 * @author Muhammad Muminov
 * @since 5/23/2024
 */
public class Clients {
    private String name;
    private String path;
    private String username;
    private String password;

    public Client() {
    }

    public Client(String name, String path, String username, String password) {
        this.name = name;
        this.path = path;
        this.username = username;
        this.password = password;
    }

    public String getPathWithStartsWith() {
        return path + "/**";
    }

    public String getRole() {
        return String.format("hasRole('%S')", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
