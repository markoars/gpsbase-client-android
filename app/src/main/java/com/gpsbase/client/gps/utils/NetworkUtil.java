package com.gpsbase.client.gps.utils;

import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.XTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MARS on 20-Jan-18.
 */

public class NetworkUtil {

    public List<XTask> getPendingTasks()
    {
        List<XTask> tasks = new ArrayList<>();
        tasks.add(new XTask(1, "Аеродром, Лисиче, Центар", "понеделник, 02.03.2017 08:00", new Date(),  R.drawable.bg_circle));
        tasks.add(new XTask(2, "Ѓорче, Влае", "среда, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
        tasks.add(new XTask(3, "Аеродром, Центар, Чаир, Бутел, Карпош 1", "петок, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));


        return tasks;
    }
}
