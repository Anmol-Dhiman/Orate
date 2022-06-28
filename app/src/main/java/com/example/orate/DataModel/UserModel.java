package com.example.orate.DataModel;

public class UserModel {

    private String userName, image, about, fullName, phoneNumber;

    public UserModel() {
    }



    public UserModel(String userName, String image, String about, String fullName, String phoneNumber) {
        this.userName = userName;
        this.image = image;
        this.about = about;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public String getFullName() {
        return fullName;
    }
}
