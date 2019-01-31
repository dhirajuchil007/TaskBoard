package com.velocityappsdj.taskboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class completedMilestone extends AppCompatActivity implements TaskAdapter.OnItemClicked {
    TextView pointsReached,rewardEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_milestone);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pointsReached=findViewById(R.id.points_reached_tv);
        rewardEarned=findViewById(R.id.reward_earned_tv);
        Intent intent=getIntent();
        int points=intent.getIntExtra(MainActivity.POINTS_REACHED,0);
        String reward=intent.getStringExtra(MainActivity.REWARD_EARNED);
        pointsReached.setText(getString(R.string.pointsreached)+" "+points+" "+getString(R.string.pointsLowercase));
        rewardEarned.setText(getString(R.string.rewardearned)+reward);
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

    @Override
    public void onItemClick(int position) {

    }
}
