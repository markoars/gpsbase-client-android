package com.gpsbase.client.gps.models;

/**
 * Created by MARS on 25-Nov-18.
 */

public class User {

    public String userUID;
    public String firstName;
    public String lastName;

    public String companyName;
    public String companyUID;

    public String currentTaskUID;

    public User(){}
    public User(String userUID,
                String firstName,
                String lastName,
                String companyName,
                String companyUID,
                String currentTaskUID) {
        this.userUID = userUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.companyUID = companyUID;
        this.currentTaskUID = currentTaskUID;
    }


    public java.lang.String getUserUID() {
        return userUID;
    }
   // public void setUserUID(String uID) { this.userUID = uID; }

    public String getFirstName() { return firstName; }
   // public void setFirstName(String firstName) {
    //    this.firstName = firstName;
    //}

    public String getLastName() { return lastName; }
   // public void setLastName(String lastName) {
   //     this.lastName = lastName;
   // }



    public String getCompanyName() {
        return companyName;
    }
   // public void setCompanyName(String companyName) {
   //     this.companyName = companyName;
    //}

    public String getCompanyUID() {
        return companyUID;
    }
  //  public void setCompanyUID(String companyUID) {
  //      this.companyUID = companyUID;
   // }


    public String getCurrentTaskUID() {
        return currentTaskUID;
    }

}
