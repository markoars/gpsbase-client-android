package com.gpsbase.client.gps.models;

/**
 * Created by MARS on 01-Dec-18.
 */

public class Worker {
    public String userID;
    public String firstName;
    public String lastName;

    public String companyName;
    public String companyID;

    public Worker(){}
    public Worker(String userID,
                String firstName,
                String lastName,
                String companyName,
                String companyID) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.companyID = companyID;
    }


    public String getUserUID() {
        return this.userID;
    }
    // public void setUserUID(String uID) { this.userUID = uID; }

    public String getFirstName() { return this.firstName; }
    // public void setFirstName(String firstName) {
    //    this.firstName = firstName;
    //}

    public String getLastName() { return this.lastName; }
    // public void setLastName(String lastName) {
    //     this.lastName = lastName;
    // }



    public String getCompanyName() {
        return this.companyName;
    }
    // public void setCompanyName(String companyName) {
    //     this.companyName = companyName;
    //}

    public String getCompanyID() {
        return this.companyID;
    }
    //  public void setCompanyUID(String companyUID) {
    //      this.companyUID = companyUID;
    // }
}
