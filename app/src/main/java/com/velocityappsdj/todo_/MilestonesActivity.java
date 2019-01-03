package com.velocityappsdj.todo_;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MilestonesActivity extends AppCompatActivity {
    List<Milestones> milestonesList;
    AppDatabase mDb;
    RecyclerView milestonesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb=AppDatabase.getsInstance(this);
        milestonesRecyclerView=findViewById(R.id.milestones_list);
        getMilestoneData();





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_milestone);
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MilestonesActivity.this,AddMilestone.class);
                startActivity(intent);

            }
        });
    }
    public void getMilestoneData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                milestonesList=mDb.tasksDao().getMilestones();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        milestonesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        MilestonesAdapter milestonesAdapter=new MilestonesAdapter(milestonesList);
                        milestonesRecyclerView.setAdapter(milestonesAdapter);
                    }
                });


            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getMilestoneData();
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
