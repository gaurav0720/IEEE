package com.example.ieee;

public class ETRPerson {

    String name, emailAdd,mobileNumber, expertise, qualification,imageURL;

    public ETRPerson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ETRPerson(String name, String emailAdd, String mobileNumber, String expertise, String qualification, String imageURL) {
        this.name = name;
        this.emailAdd = emailAdd;
        this.mobileNumber = mobileNumber;
        this.expertise = expertise;
        this.qualification = qualification;
        this.imageURL = imageURL;
    }
}
