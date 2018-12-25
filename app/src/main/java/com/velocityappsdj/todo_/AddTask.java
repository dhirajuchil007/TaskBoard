package com.velocityappsdj.todo_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {
    TextView points;
    Button submit;
    private AppDatabase mDb;
    EditText taskDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        points=findViewById(R.id.task_points_tv_addtask);
        mDb=AppDatabase.getsInstance(getApplicationContext());
        SeekBar seekBar=findViewById(R.id.seekBar);
        taskDescription= findViewById(R.id.task_desc);

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
              final  Tasks tasks=new Tasks(taskDescription.getText().toString(),Integer.parseInt(points.getText().toString()),0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.tasksDao().insert(tasks);
                    }
                }).start();

                onBackPressed();

            }
        });




    }
}
