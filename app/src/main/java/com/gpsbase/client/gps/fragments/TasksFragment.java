package com.gpsbase.client.gps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.activities.StatusActivity;
import com.gpsbase.client.gps.models.Client;
import com.gpsbase.client.gps.models.Companies;
import com.gpsbase.client.gps.models.Company;
import com.gpsbase.client.gps.models.NewTasks;
import com.gpsbase.client.gps.models.NewUsers;
import com.gpsbase.client.gps.models.Position;
import com.gpsbase.client.gps.models.TaskCoordinates;
import com.gpsbase.client.gps.models.XTask;
import com.gpsbase.client.gps.adapters.RVTasksAdapter;
import com.gpsbase.client.gps.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import static com.gpsbase.client.gps.utils.DatabaseHelper.POSITIONS_TEMP_TABLE;


public class TasksFragment extends Fragment {

    private List<XTask> tasks;
    private RecyclerView rv;
    private RVTasksAdapter adapter;
    NetworkUtil networkUtil;


    DatabaseReference distributionComapniesRef = FirebaseDatabase.getInstance().getReference("DistributionCompanies");
    DatabaseReference metoCompanyClientsRef = FirebaseDatabase.getInstance().getReference("DistributionCompanies/Meto Flyer company/clients");
    DatabaseReference metoPositionsRef = FirebaseDatabase.getInstance().getReference("DistributionCompanies/Meto Flyer company/clients/Tinex/tasks/1/positions");

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("");
    DatabaseReference firebaseRootRef = FirebaseDatabase.getInstance().getReference("NewTasks");
    DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("Tasks");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_view_tasks,null);

        rv = view.findViewById(R.id.rvTasks);
        networkUtil = new NetworkUtil();


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return view;
    }


    private void initializeData(){
       // tasks = networkUtil.getPendingTasks(adapter);
       // adapter.rebindData();
        tasks = new ArrayList<>();
        getPendingTasks();


        /// Root companies

       /* List<Position> positions = new ArrayList<>();
        Position position = new Position();
        position.setId(1);
        position.setLatitude(11111);
        position.setLongitude(22222);
        position.setTaskId(1);
        position.setUserId("MARS");
        positions.add(position);



        List<XTask> tasks = new ArrayList<>();
        XTask task = new XTask();
        task.taskId = 1;
        task.taskDescription = "Task 1";
        tasks.add(task);

        List<XTask> tasks2 = new ArrayList<>();
        XTask task2 = new XTask();
        task2.taskId = 1;
        task2.taskDescription = "Task 2";
        tasks.add(task2);

        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.id = 1;
        client.name = "Tinex";
        client.tasks = tasks;
        clients.add(client);


        Client client2 = new Client();
        client2.id = 2;
        client2.name = "Vero";
        client2.tasks = tasks2;
        clients.add(client2);


        List<Company> companiesList = new ArrayList<>();
        Company company = new Company();
        company.id = 1;
        company.name = "Meto Flyer company";
        company.clients = clients;
        companiesList.add(company);


        Companies companies = new Companies();
        companies.description = "Distribution companies";
        companies.companies = companiesList;


        metoPositionsRef.child(String.valueOf(position.getId())).setValue(position, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });
*/
        // Пробав да го креирам дрвото само со вгнездени објекти, но имаше проблем при сетирање на примарниот клуч
        // Затоа ќе го градам дрвото чекор по чекот програматски, и би било ок според мене
        // Види го дрвото во фиребасе како изгледа сега, со командиве искоментирани доле.
        // Плус од кога ке го испраксирам градењето на дрвото и дефинирам командите кога ќе се генерира кој објект
        // потоа ќе размислам колку би било тешко да ја реструктурирам базата (за да биде лесно да се зимаат коорнинати од одредени корисници а не на сите од тој таск
        // , или вака че тераме со наједноставниот принцип
       /* metoCompanyRef.child("2").setValue(task, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });
*/

       /* metoCompanyClientsRef.child(client2.getName()).setValue(client2, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });*/


       /* distributionComapniesRef.child(company.getName()).setValue(company, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });*/


        // Автоматско градење на дрвото - не  е ОК поради примарниот клуч (ид е)
     /*   rootRef.child("DistributionCompanies").setValue(companies, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });*/



       /* NewTasks newTasks = new NewTasks();
        ArrayList<TaskCoordinates> newTaskCoordinateList = new ArrayList<TaskCoordinates>();
        Position position = new Position();
        TaskCoordinates taskCoordinates = new TaskCoordinates();
        taskCoordinates.position = position;
        taskCoordinates.userId = "1";

        newTaskCoordinateList.add(taskCoordinates);
        newTasks.taskId = "4444";
        newTasks.taskCoordinates = newTaskCoordinateList;*/

      /*  firebaseRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                    NewTasks message = messageSnapshot.getValue(NewTasks.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, databaseError.toString();

            }
        });*/

        //firebaseRootRef.child(newTasks.taskId).setValue()



       /* firebaseRootRef.child(newTasks.taskId).setValue(newTasks, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                   // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                   // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                  //  retry();
                }
                //unlock();
            }
        });*/




       /* rootRef.child("New").setValue(newTasks, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    // delete(POSITIONS_TEMP_TABLE, position);
                    System.out.println("Firebase. Error = " + error);
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                    int a = 4;
                    // StatusActivity.addMessage(context.getString(R.string.status_send_fail));
                    //  retry();
                }
                //unlock();
            }
        });*/
    }

    private void initializeAdapter(){
        adapter = new RVTasksAdapter(tasks, this.getContext());
        rv.setAdapter(adapter);
    }


    @Override
    public void onResume(){
        super.onResume();

        adapter.rebindData();
    }






    public void getPendingTasks()
    {
        databaseTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                //ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());


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
    }
}

