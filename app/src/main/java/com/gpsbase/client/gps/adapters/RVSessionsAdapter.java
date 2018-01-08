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
        TextView clientName;
        TextView sessionDescription;
        TextView sessionStart;
        ImageView image;
        Session session;

        SessionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_session);
            clientName = (TextView)itemView.findViewById(R.id.client_name);
            sessionDescription = (TextView)itemView.findViewById(R.id.session_description);
            sessionStart = itemView.findViewById(R.id.session_start);
            //image = (ImageView)itemView.findViewById(R.id.person_photo);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), SessionActivity.class);
                    v.getContext().startActivity(intent);
                    //Toast.makeText(v.getContext(), "os version is: " + feed.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });*/

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
        sessionViewHolder.clientName.setText(sessionText);
        sessionViewHolder.sessionDescription.setText(sessions.get(i).sessionDescription);
        sessionViewHolder.sessionStart.setText(sessions.get(i).sessionStartString);
        sessionViewHolder.session = sessions.get(i);
       // sessionViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }


}