package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARS on 07-Oct-18.
 */

public class Companies {

    public List<Company> companies = new ArrayList<>();
    public String description;


    public Companies() {}
    public Companies(List<Company> _companies, String _description)
    {
        companies = _companies;
        description = _description;
    }


    public String getDescription()
    {
        return description;
    }

    public List<Company> getCompanies() {
        return companies;
    }


}
