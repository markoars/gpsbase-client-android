package com.gpsbase.client.gps.utils;

import android.content.SharedPreferences;
import android.content.Context;
/**
 * Created by MARS on 10-Mar-19.
 */

import com.gpsbase.client.gps.models.User;

/**
 * Created by tundealao on 29/03/15.
 */
public class UserLocalStorage {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }



    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("userUID", user.getUserUID());
        userLocalDatabaseEditor.putString("firstName", user.getFirstName());
        userLocalDatabaseEditor.putString("lastName", user.getLastName());
        userLocalDatabaseEditor.putString("companyName", user.getCompanyName());
        userLocalDatabaseEditor.putString("companyUID", user.getCompanyUID());
        userLocalDatabaseEditor.putString("currentTaskUID", user.getCurrentTaskUID());
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void setCurrentTaskUID(String currentTaskUID) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("currentTaskUID", currentTaskUID);
        userLocalDatabaseEditor.commit();
    }
    public String getCurrentTaskUID() {
        String currentTaskUID = userLocalDatabase.getString("currentTaskUID", "");

        return currentTaskUID;
    }


    public void setCurrentClientUID(String currentClientUID) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("currentClientUID", currentClientUID);
        userLocalDatabaseEditor.commit();
    }


    public String getCurrentClientUID() {
        String currentClientUID = userLocalDatabase.getString("currentClientUID", "");

        return currentClientUID;
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
      //  if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
       //     return null;
       // }


        String userUID = userLocalDatabase.getString("userUID", "");
        String firstName = userLocalDatabase.getString("firstName", "");
        String lastName = userLocalDatabase.getString("lastName", "");
        String companyName = userLocalDatabase.getString("companyName", "");
        String companyUID = userLocalDatabase.getString("companyUID", "");
        String currentTaskUID = userLocalDatabase.getString("currentTaskUID", "");


        User user = new User(userUID, firstName, lastName, companyName, companyUID, currentTaskUID);
        return user;
    }
}