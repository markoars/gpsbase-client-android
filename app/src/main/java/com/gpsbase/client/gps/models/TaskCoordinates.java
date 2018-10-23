package com.gpsbase.client.gps.models;

/**
 * Created by MARS on 19-Aug-18.
 */

public class TaskCoordinates {

    public String userId;
    public Position position;



    public TaskCoordinates() {}
    public TaskCoordinates(String _userId, Position _position)
    {
        userId = _userId;
        position = _position;
    }


    public String getUserId() {
        return userId;
    }


    public Position getPosition() {
        return position;
    }
}
