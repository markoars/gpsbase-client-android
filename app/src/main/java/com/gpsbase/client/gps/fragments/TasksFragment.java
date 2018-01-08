package com.gpsbase.client.gps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.XTask;
import com.gpsbase.client.gps.adapters.RVTasksAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class TasksFragment extends Fragment {

    private List<XTask> tasks;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // return inflater.inflate(R.layout.recycler_view_tasks,null);


        View view =  inflater.inflate(R.layout.recycler_view_tasks,null);

        rv=(RecyclerView)view.findViewById(R.id.rvTasks);


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return view;
    }


    private void initializeData(){
        tasks = new ArrayList<>();
        tasks.add(new XTask(1, "Аеродром, Лисиче, Центар", "понеделник, 02.03.2017 08:00", new Date(),  R.drawable.bg_circle));
        tasks.add(new XTask(2, "Ѓорче, Влае", "среда, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
        tasks.add(new XTask(3, "Аеродром, Центар, Чаир, Бутел, Карпош 1", "петок, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
    }

    private void initializeAdapter(){
        RVTasksAdapter adapter = new RVTasksAdapter(tasks);
        rv.setAdapter(adapter);
    }
}

