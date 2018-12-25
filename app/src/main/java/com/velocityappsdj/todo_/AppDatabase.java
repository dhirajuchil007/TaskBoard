package com.velocityappsdj.todo_;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database( entities ={Tasks.class},version = 1,exportSchema = false)
public  abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "tasksDB";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context)
    {
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                //Temporary
                sInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).build();
            }
        }
        return sInstance;
    }
    public abstract TasksDao tasksDao();
}
