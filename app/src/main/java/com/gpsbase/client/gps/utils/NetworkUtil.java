package com.gpsbase.client.gps.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.adapters.RVTasksAdapter;
import com.gpsbase.client.gps.models.Position;
import com.gpsbase.client.gps.models.XTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MARS on 20-Jan-18.
 */

public class NetworkUtil {


    DatabaseReference firebaseRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("Tasks");

    public NetworkUtil()
    {

    }

    public List<XTask> getTasks(DataSnapshot snapshot)
    {
        final List<XTask> tasks = new ArrayList<>();

        GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
        Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
        ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());


        for (Map.Entry<String, Object> entry : objectHashMap.entrySet()){

            Object objectTask = entry.getValue();

            HashMap<String, Object> testTask = (HashMap<String, Object>) objectTask;


            long taskId = (long) testTask.get("taskId");
            String taskDescr = (String) testTask.get("taskDescription");
            String taskStartString = (String) testTask.get("taskStartString");
            long photoId = (long) testTask.get("photoId");


            tasks.add(new XTask(taskId, taskDescr, taskStartString, new Date(), 3,  photoId));
        }

        return tasks;
    }

    public List<XTask> getPendingTasks(final RVTasksAdapter adapter)
    {

        final List<XTask> tasks = new ArrayList<>();

       // DataSnapshot contactSnapshot = dataSnapshot.child("contacts");


        databaseTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());


                for (Map.Entry<String, Object> entry : objectHashMap.entrySet()){

                    Object objectTask = entry.getValue();

                    HashMap<String, Object> testTask = (HashMap<String, Object>) objectTask;


                    long taskId = (long) testTask.get("taskId");
                    String taskDescr = (String) testTask.get("taskDescription");
                    String taskStartString = (String) testTask.get("taskStartString");
                    long photoId = (long) testTask.get("photoId");


                    tasks.add(new XTask(taskId, taskDescr, taskStartString, new Date(), 3,  photoId));
                }

                adapter.rebindData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Log.e(TAG, databaseError.toString();

            }
        });


       // XTask task1 = new XTask(1, "Аеродром, Лисиче, Центар", "понеделник, 02.03.2017 08:00", new Date(),  R.drawable.bg_circle);
       // XTask task2 = new XTask(2, "Ѓорче, Влае", "среда, 02.03.2017 08:00", new Date(), R.drawable.bg_circle);
       // XTask task3 = new XTask(3, "Аеродром, Центар, Чаир, Бутел, Карпош 1", "петок, 02.03.2017 08:00", new Date(), R.drawable.bg_circle);



       // databaseTasks.push().setValue(task1);
       // databaseTasks.push().setValue(task2);
      //  databaseTasks.push().child(String.valueOf(1)).setValue(task2);
       // databaseTasks.push().child(String.valueOf(1)).setValue(task3);

       // tasks.add(new XTask(1, "Аеродром, Лисиче, Центар", "понеделник, 02.03.2017 08:00", new Date(),  R.drawable.bg_circle));
       // tasks.add(new XTask(2, "Ѓорче, Влае", "среда, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
       // tasks.add(new XTask(3, "Аеродром, Центар, Чаир, Бутел, Карпош 1", "петок, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
       // tasks.add(task1);
       // tasks.add(task2);
       // tasks.add(task3);


        return tasks;
    }
}
