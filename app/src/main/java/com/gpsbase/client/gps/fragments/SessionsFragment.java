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
import com.gpsbase.client.gps.models.Session;
import com.gpsbase.client.gps.adapters.RVSessionsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class SessionsFragment extends Fragment {

    private List<Session> sessions;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // return inflater.inflate(R.layout.recycler_view_sessions,null);


        View view =  inflater.inflate(R.layout.recycler_view_sessions,null);

        rv=(RecyclerView)view.findViewById(R.id.rvSessions);


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return view;
    }


    private void initializeData(){
        sessions = new ArrayList<>();
        sessions.add(new Session(1, "Аеродром, Лисиче, Центар", "понеделник, 02.03.2017 08:00", new Date(),  R.drawable.bg_circle));
        sessions.add(new Session(2, "Ѓорче, Влае", "среда, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
        sessions.add(new Session(3, "Аеродром, Центар, Чаир, Бутел, Карпош 1", "петок, 02.03.2017 08:00", new Date(), R.drawable.bg_circle));
    }

    private void initializeAdapter(){
        RVSessionsAdapter adapter = new RVSessionsAdapter(sessions);
        rv.setAdapter(adapter);
    }
}

