package com.velocityappsdj.todo_;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface TasksDao {
    @Query("select * from tasks where completed=0")
    List<Tasks> loadAllTasks();

    @Insert
    void insert(Tasks task);

    @Query("update tasks set completed=1 where id=:id")
    void completeTask(int id);

    @Query("update tasks set completed=0 where id =:id")
    void undoComplete(int id);
}
