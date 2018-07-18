package com.sp.mgm.trustmebank.model;

public class Account {

    private int id;

    private String username;

    private double balance;

    private String token;

    public Account() {
    }

    public Account(String username, String token) {

        this.username = username;
        this.token = token;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usename) {
        this.username = usename;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
