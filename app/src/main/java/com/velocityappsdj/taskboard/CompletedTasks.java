package com.velocityappsdj.taskboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class CompletedTasks extends AppCompatActivity {
    RecyclerView completedTasksList;
    List<Tasks> completed;
    AppDatabase mDb;
    TextView noCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        completedTasksList=findViewById(R.id.completed_tasks_list);
        mDb=AppDatabase.getsInstance(getApplicationContext());
        noCompleted=findViewById(R.id.no_completed_tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                completed=mDb.tasksDao().getCompletedTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(completed.size()==0)
                        {
                            noCompleted.setVisibility(View.VISIBLE);
                        }
                        else
                            noCompleted.setVisibility(View.INVISIBLE);

                    }
                });

                completedTasksList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                TaskAdapter t=new TaskAdapter(completed);
                completedTasksList.setAdapter(t);


            }
        }).start();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
