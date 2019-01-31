package com.velocityappsdj.taskboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MilestonesActivity extends AppCompatActivity {
    List<Milestones> milestonesList;
    AppDatabase mDb;
    RecyclerView milestonesRecyclerView;
    private AdView mAdView;
    TextView noMilestones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb=AppDatabase.getsInstance(this);
        noMilestones=findViewById(R.id.no_milestones_tv);
        milestonesRecyclerView=findViewById(R.id.milestones_list);
       // MobileAds.initialize(this, getString(R.string.admobappid));
        mAdView = findViewById(R.id.adView);
        initBannerAds();
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
                        if(milestonesList.size()==0)
                        {
                            noMilestones.setVisibility(View.VISIBLE);
                        }
                        else {
                            noMilestones.setVisibility(View.INVISIBLE);
                            milestonesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            MilestonesAdapter milestonesAdapter = new MilestonesAdapter(milestonesList);
                            milestonesRecyclerView.setAdapter(milestonesAdapter);
                        }
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

    public void initBannerAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
}
