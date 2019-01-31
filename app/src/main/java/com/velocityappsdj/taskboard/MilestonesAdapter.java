package com.velocityappsdj.taskboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MilestonesAdapter  extends RecyclerView.Adapter<MilestonesAdapter.MyViewHolder>{

    List<Milestones> milestonesList;

    public MilestonesAdapter(List<Milestones> milestonesList) {
        this.milestonesList = milestonesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.milestone_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.points.setText(String.valueOf(milestonesList.get(position).totalPoints));
        holder.reward.setText(milestonesList.get(position).reward);
        holder.itemView.setTag(milestonesList.get(position).id);
        if(milestonesList.get(position).completed==1)
        {
            holder.completed.setVisibility(View.VISIBLE);
        }
        else {
            holder.completed.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return milestonesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView points;
        TextView reward;
        TextView completed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            points=itemView.findViewById(R.id.points_tv_row_item);
            reward=itemView.findViewById(R.id.reward_tv_row_item);
            completed=itemView.findViewById(R.id.completed_tv);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
