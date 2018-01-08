package com.gpsbase.client.gps.adapters;

/**
 * Created by Marko on 11/4/2017.
 */

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.activities.SessionActivity;
import com.gpsbase.client.gps.models.Session;

import java.util.List;

public class RVSessionsAdapter extends RecyclerView.Adapter<RVSessionsAdapter.SessionViewHolder> {

    public static class SessionViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView sessionIdTxt;
        TextView sessionDescriptionTxt;
        TextView sessionStartTxt;
        //ImageView image;
        Session session;

        SessionViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_session);
            sessionIdTxt = itemView.findViewById(R.id.cardSession_session_id);
            sessionDescriptionTxt = itemView.findViewById(R.id.cardSession_session_description);
            sessionStartTxt = itemView.findViewById(R.id.session_start);
            //image = (ImageView)itemView.findViewById(R.id.person_photo);



            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (view.getContext(), SessionActivity.class);
                    i.putExtra("sessionId", session.sessionId);
                    i.putExtra("sessionDescription",session.sessionDescription);
                    view.getContext().startActivity(i);
                }
            });
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
        String sessionText = "#" + Integer.toString(sessions.get(i).sessionId);
        sessionViewHolder.sessionIdTxt.setText(sessionText);
        sessionViewHolder.sessionDescriptionTxt.setText(sessions.get(i).sessionDescription);
        sessionViewHolder.sessionStartTxt.setText(sessions.get(i).sessionStartString);
        sessionViewHolder.session = sessions.get(i);
       // sessionViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }


}