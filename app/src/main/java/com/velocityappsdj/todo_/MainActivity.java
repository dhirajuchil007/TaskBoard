package com.velocityappsdj.todo_;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase mDb;
    RecyclerView taskList;
    TextView noTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskList=findViewById(R.id.task_list);
        noTask=findViewById(R.id.no_task_tv);
        mDb=AppDatabase.getsInstance(getApplicationContext());
        getData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);

            }
        });
    }

    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                 final List<Tasks> t=mDb.tasksDao().loadAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(t.size()==0)
                        {
                            noTask.setVisibility(View.VISIBLE);
                            return;
                        }

                        taskList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        TaskAdapter taskAdapter=new TaskAdapter(t);
                        taskList.setAdapter(taskAdapter);
                        noTask.setVisibility(View.INVISIBLE);


                        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // mDb.tasksDao().completeTask((int)viewHolder.itemView.getTag());
                                       // int pos=(int)viewHolder.itemView.getTag();
                                        int id=(int)viewHolder.itemView.getTag();
                                        mDb.tasksDao().completeTask(id);
                                        Log.d("IDIS", "run: "+id);
                                        getData();
                                    }
                                }).start();


                            }
                        }).attachToRecyclerView(taskList);


                    }
                });

                Log.d("TEST", "run: "+t.size());
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

