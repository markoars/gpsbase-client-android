package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARS on 07-Oct-18.
 */

public class Client {


    public int id;
    public String name; // IKEA, Amazon

    public List<XTask> tasks = new ArrayList<>();    // Taks: Aerodrom1, Aerodrom2

    public Client(){}
    public Client(int _id, String _name, List<XTask> _tasks)
    {
        id = _id;
        name = _name;
        tasks = _tasks;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<XTask> getTasks() {
        return tasks;
    }
}
