package com.sp.mgm.trustmebank.model;

public class Account {

    private String usename;

    private String token;

    public Account() { }

    public Account(String usename, String token) {

        this.usename = usename;
        this.token = token;

    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
