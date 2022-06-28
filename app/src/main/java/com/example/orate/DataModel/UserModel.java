package com.example.orate.DataModel;

public class UserModel {

    private String userName, image, about, fullName, phoneNumber, isAvailable, incoming, isConnected;

    public UserModel() {
    }


    public UserModel(String userName, String image, String about, String fullName, String phoneNumber, String isAvailable, String incoming, String isConnected) {
        this.userName = userName;
        this.image = image;
        this.about = about;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.isAvailable = isAvailable;
        this.incoming = incoming;
        this.isConnected = isConnected;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public String getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(String isConnected) {
        this.isConnected = isConnected;
    }
}
