package com.gpsbase.client.gps.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marko on 11/4/2017.
 */

public class Session {
    public String clientName;
    public String sessionDescription;
    public Date sessionStart;
    public String sessionStartString;
    public int photoId;

    public Session(String clientName,
                   String sessionDescription,
                   String sessionStartString,
                   Date sessionStart,
                   int photoId) {
        this.clientName = clientName;
        this.sessionDescription = sessionDescription;
        this.sessionStart = getSampleDateTime().getTime();
        this.sessionStartString = sessionStartString;
        this.photoId = photoId;
    }


    private Calendar getSampleDateTime()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 6);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return  cal;
    }
}
