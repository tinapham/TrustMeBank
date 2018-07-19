package com.sp.mgm.trustmebank.model;

public class Transaction {

    private String id;

    private String fromUser;

    private String toUser;

    private double amount;

    private String createdAt;

    private boolean isSend; //true = send transaction - false = receive transaction

    public Transaction(String id, String fromUser, String toUser, double amount, String createdAt) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.createdAt = createdAt;
        this.isSend = false;
    }

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setSend (String username) {
        this.isSend = !username.equals(this.toUser);
    }

    public boolean isSend() {
        return isSend;
    }

    public String getName() {
        return isSend ? toUser : fromUser;
    }

}
