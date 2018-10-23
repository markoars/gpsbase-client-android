package com.gpsbase.client.gps.models;

/**
 * Created by MARS on 07-Oct-18.
 */

public class Coordinate {

    public String userId;
    public Position position;



    public Coordinate() {}
    public Coordinate(String _userId, Position _position)
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
