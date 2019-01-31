package com.velocityappsdj.taskboard;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Tasks {
    @PrimaryKey(autoGenerate = true)
    int id;
    String desc;
    int priority;
    int completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Tasks(int id, String desc, int priority, int completed) {
        this.id = id;
        this.desc = desc;
        this.priority = priority;
        this.completed = completed;
    }
    @Ignore
    public Tasks(String desc, int priority, int completed) {

        this.desc = desc;
        this.priority = priority;
        this.completed=completed;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
