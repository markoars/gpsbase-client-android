package com.gpsbase.client.gps.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.User;
import com.gpsbase.client.gps.models.XTask;
import com.gpsbase.client.gps.adapters.RVTasksAdapter;
import com.gpsbase.client.gps.utils.NetworkUtil;
import com.gpsbase.client.gps.utils.UserLocalStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TasksFragment extends Fragment {

    private List<XTask> tasks;
    private RecyclerView rv;
    private RVTasksAdapter adapter;
    NetworkUtil networkUtil;
    public UserLocalStorage localStorage;
    public User loggedUser;
    public int tabNumber = 0;


    public TasksFragment(int _tabNum)
    {
        tabNumber = _tabNum;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_view_tasks,null);

        rv = view.findViewById(R.id.rvTasks);
        networkUtil = new NetworkUtil();


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        localStorage = new UserLocalStorage(this.getContext());
        loggedUser = localStorage.getLoggedInUser();

        initializeData();
        initializeAdapter();

        viewLoaded = true;
        return view;
    }


    private void initializeData(){
        tasks = new ArrayList<>();
        getPendingTasks(true);


        /// Root companies

        /*List<Position> positions = new ArrayList<>();
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
        task.taskDescription = "Аеродром 1 тест";
        task.clientId = 1;
        Date date = new Date();
        task.taskStart = date;
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
       // client.tasks = tasks;
        clients.add(client);


        Client client2 = new Client();
        client2.id = 3;
        client2.name = "Vero 2";
       // client2.tasks = tasks2;
        clients.add(client2);

        ClientDetails clientDetails1 = new ClientDetails();
        clientDetails1.id = 1;
        clientDetails1.name = "Tinex";
        clientDetails1.details = "Tinex details";



        List<Company> companiesList = new ArrayList<>();
        Company company = new Company();
        company.id = 1;
        company.name = "Meto Flyer company";
       // company.clients = clients;
        companiesList.add(company);


        Companies companies = new Companies();
        companies.description = "Distribution companies";
        companies.companies = companiesList;

        Coordinate coordinate = new Coordinate("7213guy13287duy12", position);

        Worker worker = new Worker();
        worker.firstName = "Marko";
        worker.lastName = "Arsovski";
        worker.companyID = "1";
        worker.companyName = "Meto flyer company";
        worker.userID = "1";

        WorkerDetails workerDetails = new WorkerDetails();
        workerDetails.firstName = "Marko";
        workerDetails.lastName = "Arsovski";
        workerDetails.companyID = "1";
        workerDetails.companyName = "Meto flyer company";
        workerDetails.userID = "1";
        workerDetails.setDetails("details text");



        WorkerTasks workerTasks = new WorkerTasks();
        workerTasks.workerId = "JXBluJp8AQZ2FVeqdMNnDbAt3RS2";//"8bWZdKAftgTSkmyQVR0o7fYFkdM2";
        workerTasks.tasks = tasks;*/

        // 0) User pays/sets trial by providing an email, and company name ->
            // 0.1) we create a new user by code and send his password by an email
            // 0.2) we create a company and set a User/Worker

       // String companyId = "-LVDzu70Z23goqujCENc";
       // String userId = "79AYbvImGUXT67zYULtxca0XEAB3";//"8bWZdKAftgTSkmyQVR0o7fYFkdM2";

        // 1) Companies

        // 2) Company clients

        // 3) Company users

        // 4) Company tasks

        // 5) Assign users to tasks -> Create worker tasks

        //////////////////////////////////////////////////////////////////////////////////////////////////////////



        /*String newCompanyKey = companiesRef.push().getKey();

        // Kokrisnikot plakja i vnesuva ime na kompanija i user email i password (nie mu generirame)
        User userNew;

        userNew = new User();
        userNew.userUID = userId;
        userNew.firstName = "Marko";
        userNew.lastName = "Arsovski";
        userNew.companyName = company.getName();
        userNew.companyUID = newCompanyKey;*/

       /* Map newCompanyAndUserData = new HashMap();

        newCompanyAndUserData.put("Company/" + newCompanyKey, company);
        newCompanyAndUserData.put("Users/"  + userId, userNew);


        // Do a deep-path update
        rootRef.updateChildren(newCompanyAndUserData, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                }
            }
        });*/



        // 2) Company clients

        // Admin is created, he can start creating clients



        /*companyClientsRef = FirebaseDatabase.getInstance().getReference("CompanyClients/" + companyId + "/Clients");
        companyClientsDetailsRef = FirebaseDatabase.getInstance().getReference("CompanyClientsDetails/" + companyId + "/Clients");


        String newRecordKey = companyClientsRef.push().getKey();

        Map companyClientsData = new HashMap();

        companyClientsData.put("CompanyClients/" + companyId + "/Clients/" + newRecordKey, client);
        companyClientsData.put("CompanyClientsDetails/" + companyId + "/Clients/" + newRecordKey, clientDetails1);


        // Do a deep-path update
        rootRef.updateChildren(companyClientsData, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                }
            }
        });*/


        // 3) Company workers -> done in 1)  workers are Users





        // 4) Company tasks

        // Admin creates a task (also can add workers to it)


    /*  DatabaseReference companyTasksRef = FirebaseDatabase.getInstance().getReference("CompanyTasks/Company/" + companyId + "/Tasks/");

        String newTaskKey = companyTasksRef.push().getKey();

        Map companyTasksMap = new HashMap();

        task.taskUID = newTaskKey;

        companyTasksMap.put("WorkerTasks/Workers/" + userId + "/Tasks/" + newTaskKey, task);
        companyTasksMap.put("WorkerTasksDetails/Workers/" + userId + "/Tasks/" + newTaskKey, task);


        // Do a deep-path update
        rootRef.updateChildren(companyTasksMap, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Firebase. Error = " + error);
                }
            }
        });
    */
        // 5) Assign users to tasks -> Create worker tasks



    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Log.d(TAG, ((Object) this).getClass().getSimpleName() + " is NOT on screen");
            int a;
        }
        else
        {
           // Log.d(TAG, ((Object) this).getClass().getSimpleName() + " is on screen");
            int b;
        }
    }


    boolean _areLecturesLoaded = false;
    boolean viewLoaded = false;
    boolean tabPendingLoaded = false;
    boolean tabCompletedLoaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewLoaded) {
          //  loadLectures(); this
            _areLecturesLoaded = true;
            ;
            getPendingTasks(false);
        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    public void getPendingTasks(boolean _calledFromInit)
    {
        DatabaseReference workerTasksRef = FirebaseDatabase.getInstance().getReference("WorkerTasks/Workers/" + loggedUser.getUserUID());
        String dateFromStr, dateToStr;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        dateFromStr = formatter.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        dateToStr = formatter.format(cal.getTime());


        if((tabPendingLoaded == false && tabNumber == 0) || (!_calledFromInit && tabCompletedLoaded == false && tabNumber == 1)) {
            workerTasksRef.child("Tasks").orderByChild("taskStatusTaskDateKey").startAt(tabNumber + dateFromStr).endAt(tabNumber + dateToStr).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tasks.clear();

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        XTask task = childSnapshot.getValue(XTask.class);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                        tasks.add(task);
                    }
                    adapter.rebindData();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Log.e(TAG, databaseError.toString();
                }
            });
        }

        if(tabNumber == 0)
        {
            tabPendingLoaded = true;
        }
        else
        {
            if(!_calledFromInit) {
                tabCompletedLoaded = true;
            }
        }

    }
}

