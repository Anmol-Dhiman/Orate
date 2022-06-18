package com.example.orate.DataModel;

public class UserModel {

    private String userName, image, about, incoming, fullName, isAvailable;


    public UserModel(String userName, String image, String about, String fullName, String incoming, String isAvailable) {
        this.userName = userName;
        this.image = image;
        this.about = about;
        this.incoming = incoming;
        this.fullName = fullName;
        this.isAvailable = isAvailable;
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
