package com.example.ieee;

public class ieeeMember {

    String jType, fName, mName, lName, emailAdd, pass, cpass, sQuestion, sqAnswer, userID;

    public ieeeMember() {
    }

    public ieeeMember(String jType, String fName, String mName, String lName, String emailAdd,
                      String pass, String cpass, String sQuestion, String sqAnswer, String userID) {
        this.jType = jType;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.emailAdd = emailAdd;
        this.pass = pass;
        this.cpass = cpass;
        this.sQuestion = sQuestion;
        this.sqAnswer = sqAnswer;
        this.userID = userID;
    }

    public String getjType() {
        return jType;
    }

    public void setjType(String jType) {
        this.jType = jType;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getsQuestion() {
        return sQuestion;
    }

    public void setsQuestion(String sQuestion) {
        this.sQuestion = sQuestion;
    }

    public String getSqAnswer() {
        return sqAnswer;
    }

    public void setSqAnswer(String sqAnswer) {
        this.sqAnswer = sqAnswer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
