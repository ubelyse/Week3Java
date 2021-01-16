package com.example.eventreserve.models;

public class Account {
    private String username;
    private String fullName;
    private String address;
    private String description;
    private String phoneNumber;
    private String dateOfBirth;

    public Account()
    {
    }

    public Account(String username, String description, String fullName, String address,
                   String phoneNumber, String dateOfBirth) {
        this.username = username;
        if(description.isEmpty())
            this.description = "Description not yet";
        else
            this.description = description;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}