package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARS on 24-Jun-18.
 */

public class Company {

    public int id;
    public String name; // Flyer distribution company 1

    public List<Client> clients = new ArrayList<>();  // Clients: IKEA,Amazon,Bricolague etc


    public Company() {}
    public Company(int _id, String _name, List<Client> _clients)
    {
        id = _id;
        name = _name;
        clients = _clients;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Client> getClients() { return  clients; }
}
