package com.example.mamanguo.getAvailableMamaNguo;

public class FetchMamaNguo {

    private int mamanguoId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int rating;

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

    public int getRating() {
        return rating;
    }
}

