package com.velocityappsdj.taskboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter   extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    List<Tasks> tasksArrayList;
    private OnItemClicked onClick;

    public TaskAdapter(List<Tasks> tasksArrayList) {
        this.tasksArrayList = tasksArrayList;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView points;
        TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            points=itemView.findViewById(R.id.task_points);
            description=itemView.findViewById(R.id.task_desc);
        }


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Tasks task=tasksArrayList.get(position);
        holder.points.setText(String.valueOf(task.priority));
        holder.description.setText(task.desc);
        holder.itemView.setTag(task.id);
        if(task.completed==0)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClick.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }


    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }


}
