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
import com.gpsbase.client.gps.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class TasksFragment extends Fragment {

    private List<XTask> tasks;
    private RecyclerView rv;
    private RVTasksAdapter adapter;
    NetworkUtil networkUtil;

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
        tasks = networkUtil.getPendingTasks();
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
}

