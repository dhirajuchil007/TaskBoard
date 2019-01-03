package com.velocityappsdj.todo_;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TotalPoints {
    @PrimaryKey(autoGenerate = true)
    int id;
    int points;

    public TotalPoints(int points) {
        this.points = points;
    }
}
