package com.velocityappsdj.taskboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class AddTask extends AppCompatActivity {
    TextView points;
    Button submit;
    private AppDatabase mDb;
    EditText taskDescription;
    private AdView mAdView;
    boolean existing;
    int id=0;
    Button deleteTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        points=findViewById(R.id.task_points_tv_addtask);
        mDb=AppDatabase.getsInstance(getApplicationContext());
        deleteTask=findViewById(R.id.delete_task_button);
        final SeekBar seekBar=findViewById(R.id.seekBar);
        taskDescription= findViewById(R.id.task_desc);
       // MobileAds.initialize(this, getString(R.string.admobappid));
        mAdView = findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId("ca-app-pub-9540086841520699/6531790584");
        Intent intent=getIntent();
        if(intent.hasExtra(MainActivity.TASK_ID))
        {existing=true;
            deleteTask.setVisibility(View.VISIBLE);
             id=intent.getIntExtra(MainActivity.TASK_ID,0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Tasks> t=mDb.tasksDao().loadTaskById(id);
                   final  Tasks task=t.get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            taskDescription.setText(task.desc);
                            seekBar.setProgress(task.priority/10-1);
                        }
                    });


                }
            }).start();

        }
        initBannerAds();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                points.setText(String.valueOf((progress+1)*10));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        submit=findViewById(R.id.submit_task);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( !validateInput())
               {
                   return;

               }
               if(existing)
               {
                   final  Tasks tasks=new Tasks(id,taskDescription.getText().toString(),Integer.parseInt(points.getText().toString()),0);
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           mDb.tasksDao().updateTask(tasks);
                       }
                   }).start();

               }
               else {
                   final Tasks tasks = new Tasks(taskDescription.getText().toString(), Integer.parseInt(points.getText().toString()), 0);
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           mDb.tasksDao().insert(tasks);
                       }
                   }).start();
               }
                onBackPressed();

            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      mDb.tasksDao().deleteTask(id);
                    }
                }).start();

                onBackPressed();
            }


        });



    }
    public boolean  validateInput(){
       if(taskDescription.getText().toString()==""||taskDescription.getText().toString()==null||taskDescription.getText().toString().matches(""))
       {
           taskDescription.setError(getString(R.string.taskneedederror));
           return false;
       }

       return true;
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

    public void initBannerAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
}
