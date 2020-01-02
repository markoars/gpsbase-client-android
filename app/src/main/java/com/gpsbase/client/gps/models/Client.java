package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARS on 07-Oct-18.
 */

public class Client {


    public int id;
    public String name; // IKEA, Amazon

    //public List<XTask> tasks = new ArrayList<>();    // Taks: Aerodrom1, Aerodrom2

    public Client(){}
    public Client(int _id, String _name)
    {
        id = _id;
        name = _name;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}


