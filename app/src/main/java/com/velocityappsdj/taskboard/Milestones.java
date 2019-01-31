package com.velocityappsdj.taskboard;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Milestones {
    @PrimaryKey(autoGenerate = true)
    int id;
    int totalPoints;
    String reward;
    int completed;
    @Ignore
    public Milestones(int totalPoints, String reward,int completed) {
        this.totalPoints = totalPoints;
        this.reward = reward;
        this.completed=completed;
    }

    public Milestones(int id, int totalPoints, String reward) {
        this.id = id;
        this.totalPoints = totalPoints;
        this.reward = reward;
    }
}


