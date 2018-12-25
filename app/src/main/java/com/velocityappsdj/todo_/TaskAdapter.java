package com.velocityappsdj.todo_;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter   extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    List<Tasks> tasksArrayList;

    public TaskAdapter(List<Tasks> tasksArrayList) {
        this.tasksArrayList = tasksArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView points;
        TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            points=itemView.findViewById(R.id.task_points);
            description=itemView.findViewById(R.id.task_desc);
        }

        @Override
        public void onClick(View v) {

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tasks task=tasksArrayList.get(position);
        holder.points.setText(String.valueOf(task.priority));
        holder.description.setText(task.desc);
        holder.itemView.setTag(task.id);

    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }


}
