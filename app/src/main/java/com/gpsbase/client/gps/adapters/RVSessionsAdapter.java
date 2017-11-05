package com.gpsbase.client.gps.adapters;

/**
 * Created by Marko on 11/4/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.Session;

import java.util.List;

public class RVSessionsAdapter extends RecyclerView.Adapter<RVSessionsAdapter.SessionViewHolder> {

    public static class SessionViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView clientName;
        TextView sessionDescription;
        TextView sessionStart;
        ImageView image;

        SessionViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_session);
            clientName = (TextView)itemView.findViewById(R.id.client_name);
            sessionDescription = (TextView)itemView.findViewById(R.id.session_description);
            sessionStart = itemView.findViewById(R.id.session_start);
            //image = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Session> sessions;

    public RVSessionsAdapter(List<Session> sessions){
        this.sessions = sessions;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_session, viewGroup, false);
        SessionViewHolder pvh = new SessionViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SessionViewHolder sessionViewHolder, int i) {
        sessionViewHolder.clientName.setText(sessions.get(i).clientName);
        sessionViewHolder.sessionDescription.setText(sessions.get(i).sessionDescription);
        sessionViewHolder.sessionStart.setText(sessions.get(i).sessionStartString);
       // sessionViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }
}