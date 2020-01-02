package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Marko on 11/4/2017.
 */

public class XTask {
    public long taskId;
    public String taskUID;
    public String taskDescription;
    public Date taskStart;
    public String taskStartString;
    public long photoId;
    public int clientId;
    public String clientUID;

    public List<Position> positions = new ArrayList<>();

    public XTask(){}
    public XTask(long taskId,
                   String taskUID,
                   String taskDescription,
                   String taskStartString,
                   Date taskStart,
                   int clientId,
                   String clientUID,
                   long photoId) {
        this.taskDescription = taskDescription;
        this.taskUID = taskUID;
        this.taskId = taskId;
        this.taskStart = getSampleDateTime().getTime();
        this.taskStartString = taskStartString;
        this.clientId = clientId;
        this.clientUID = clientUID;
        this.photoId = photoId;
    }



    public long getTaskId() {
        return taskId;
    }
    public String getTaskUID() {
        return taskUID;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public Date getTaskStart() {
        return taskStart;
    }
    public String getTaskStartString() { return  taskStartString; }
    public int getClientId() { return clientId;}
    public String getClientUID() { return clientUID; }
    public long getPhotoId() { return photoId; }
    public List<Position> getPositions() { return positions; }

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

