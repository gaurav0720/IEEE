package com.example.ieee;

public class CollegeEvents {

    String collegeName, committeeMember, eventDate, eventETRPerson, eventTitle;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCommitteeMember() {
        return committeeMember;
    }

    public void setCommitteeMember(String committeeMember) {
        this.committeeMember = committeeMember;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventETRPerson() {
        return eventETRPerson;
    }

    public void setEventETRPerson(String eventETRPerson) {
        this.eventETRPerson = eventETRPerson;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public CollegeEvents(String collegeName, String committeeMember, String eventDate, String eventETRPerson, String eventTitle) {
        this.collegeName = collegeName;
        this.committeeMember = committeeMember;
        this.eventDate = eventDate;
        this.eventETRPerson = eventETRPerson;
        this.eventTitle = eventTitle;
    }

    public CollegeEvents() {
    }
}
