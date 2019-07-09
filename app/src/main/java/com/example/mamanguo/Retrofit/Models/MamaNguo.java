package com.example.mamanguo.Retrofit.Models;

public class MamaNguo {

    private int mamanguoId;
    private String firstName;

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isCompleted() {
        return completed;
    }

    private String lastName;
    private String phoneNumber;
    private float rating;
    private boolean accepted;
    private boolean completed;

    private String status;

    public String getStatus() {
        return status;
    }

    public int getMamanguoId() {
        return mamanguoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getRating() {
        return rating;
    }
}

