package com.example.mamanguo.Retrofit;

public class User {

    private Integer userId;
    private int roleId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    private boolean status;
    private String message;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserId() {
        return userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.roleId = 1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}