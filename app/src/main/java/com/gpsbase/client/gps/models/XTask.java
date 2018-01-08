package com.gpsbase.client.gps.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marko on 11/4/2017.
 */

public class XTask {
    public int taskId;
    public String taskDescription;
    public Date taskStart;
    public String taskStartString;
    public int photoId;

    public XTask(int taskId,
                   String taskDescription,
                   String taskStartString,
                   Date taskStart,
                   int photoId) {
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStart = getSampleDateTime().getTime();
        this.taskStartString = taskStartString;
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
