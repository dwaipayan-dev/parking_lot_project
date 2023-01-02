package com.parkinglot.manage.ResponseModel;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String token;

    // Empty Constructor not needed in response body as JSON is not parsed
    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "JwtResponse [token=" + token + "]";
    }

}
