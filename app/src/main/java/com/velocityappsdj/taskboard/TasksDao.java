package com.velocityappsdj.taskboard;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TasksDao {
    @Query("select * from tasks where completed=0")
    List<Tasks> loadAllTasks();

    @Query("select * from tasks where completed=1")
    List<Tasks> getCompletedTasks();

    @Insert
    void insert(Tasks task);

    @Query("update tasks set completed=1 where id=:id")
    void completeTask(int id);

    @Query("update tasks set completed=0 where id =:id")
    void undoComplete(int id);

    @Delete
    void removeTask(Tasks tasks);

    @Insert
    void insertMilestone(Milestones milestone);

    @Delete
    void removeMilestone(Milestones milestones);

    @Insert
    void insertTotalPoints(TotalPoints points);

    @Query("select * from totalpoints")
    List<TotalPoints> getTotalPoints();

    @Query("select * from milestones order by totalPoints desc")
    List<Milestones> getMilestones();

    @Query("select * from milestones where totalPoints>(select points from totalpoints) order by totalPoints")
    List<Milestones> getNextMileStone();

    @Query("update totalpoints set points=:pts")
    void updateTotalPoints(int pts);

    @Query("select * from tasks where id=:id")
    Tasks getTaskFromId(int id);

    @Query("select * from milestones m,totalpoints t where m.totalPoints<=t.points and m.completed=0 order by m.totalPoints desc" )
    List<Milestones> getCompletedMilestones();

    @Query("update milestones set completed=1 where totalPoints<=(select points from totalpoints)")
    int updateCompletedMilestone();

    @Query("delete from milestones")
    void nukeMilestones();

    @Query("delete from tasks")
    void nukeTasks();

    @Query("select * from tasks where id=:id")
    List<Tasks> loadTaskById(int id);

    @Update(onConflict = REPLACE)
    void updateTask(Tasks tasks);

    @Query("delete from tasks where id=:id")
    void deleteTask(int id);





}
