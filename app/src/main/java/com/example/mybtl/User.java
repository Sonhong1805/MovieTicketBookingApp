package com.example.mybtl;

public class User {
    private int Id;
    private String Fullname;
    private String Email;
    private String Password;
    private String PhoneNumber;

    public User(int id, String fullname, String email, String password, String phoneNumber) {
        Id = id;
        Fullname = fullname;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
