package com.gpsbase.client.gps.adapters;

/**
 * Created by Marko on 11/4/2017.
 */

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpsbase.client.MainApplication;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.activities.TaskActivity;
import com.gpsbase.client.gps.models.XTask;

import java.util.List;

public class RVTasksAdapter extends RecyclerView.Adapter<RVTasksAdapter.TaskViewHolder> {

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTaskId;
        TextView txtTaskDescription;
        TextView txtTaskStart;
        TextView txtTaskStatus;
        //ImageView image;
        XTask task;

        TaskViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_task);
            txtTaskId = itemView.findViewById(R.id.cardTask_task_id);
            txtTaskDescription = itemView.findViewById(R.id.cardTask_task_description);
            txtTaskStart = itemView.findViewById(R.id.task_start);
            txtTaskStatus = itemView.findViewById(R.id.card_task_task_status);


            //image = (ImageView)itemView.findViewById(R.id.person_photo);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (view.getContext(), TaskActivity.class);
                    i.putExtra("taskId", task.getTaskId());
                    i.putExtra("taskUID", task.getTaskUID());
                    i.putExtra("taskDescription", task.getTaskDescription());
                    i.putExtra("clientId", task.getClientId());
                    i.putExtra("clientUID", task.getClientUID());

                    view.getContext().startActivity(i);
                }
            });
        }
    }

    List<XTask> tasks;
    Context context;

    public RVTasksAdapter(List<XTask> tasks, Context context){
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_task, viewGroup, false);
        TaskViewHolder tvh = new TaskViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        String taskText = "#" + Long.toString(tasks.get(i).taskId);
        taskViewHolder.txtTaskId.setText(taskText);
        taskViewHolder.txtTaskDescription.setText(tasks.get(i).taskDescription);
        taskViewHolder.txtTaskStart.setText(tasks.get(i).taskStartString);
        taskViewHolder.task = tasks.get(i);

        String currentTrackingTaskId = ((MainApplication) context.getApplicationContext()).getCurrentTrackingTaskUID();

        if(context != null) {
            if (currentTrackingTaskId == tasks.get(i).taskUID) {
                taskViewHolder.txtTaskStatus.setVisibility(View.VISIBLE);
            }
            else {
                taskViewHolder.txtTaskStatus.setVisibility(View.GONE);
            }
        }
       // taskViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }



    public void rebindData(){
        notifyDataSetChanged();
    }

}