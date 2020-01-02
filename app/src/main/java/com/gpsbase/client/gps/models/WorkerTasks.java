package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARS on 02-Dec-18.
 */

public class WorkerTasks {
    public String workerId;

    public List<XTask> tasks = new ArrayList<>();

    public WorkerTasks(){}
    public WorkerTasks(String _workerId, List<XTask> _tasks)
    {
        this.workerId = _workerId;
        tasks = _tasks;
    }



    public String getWorkerId() {
        return workerId;
    }
    public List<XTask> getTasks() {
        return tasks;
    }
}
