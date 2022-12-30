package com.parkinglot.manage.RequestModel;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    // private static final long serialVersionUid = 0001L;

    private String username;

    private String password;

    // need default constructor for JSON parsing
    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "JwtRequest [username=" + username + ", password=" + password + "]";
    }

}
