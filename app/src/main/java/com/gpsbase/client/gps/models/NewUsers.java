package com.gpsbase.client.gps.models;

/**
 * Created by MARS on 29-Jul-18.
 */

public class NewUsers {

    public int userId;


    public NewUsers() {}
    public NewUsers(int _userId)
    {
        userId = _userId;
    }



    public int getUserId() {
        return userId;
    }


}
