package com.gpsbase.client.gps.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MARS on 29-Jul-18.
 */

public class NewTasks {

    public String taskId;
    public List<TaskCoordinates> taskCoordinates;



    public NewTasks() {}
    public NewTasks(String _taskId, List<TaskCoordinates> _taskCoordinates)
    {
        taskId = _taskId;
        taskCoordinates = _taskCoordinates;
    }


    public String getTaskId() {
        return taskId;
    }

    public List<TaskCoordinates> getTaskCoordinates() {
        return taskCoordinates;
    }

}
