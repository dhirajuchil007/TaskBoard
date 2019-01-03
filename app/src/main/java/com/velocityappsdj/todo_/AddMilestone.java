package com.velocityappsdj.todo_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMilestone extends AppCompatActivity {
    EditText reward,pointsNeeded;
    Button submitMilestone;
    AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milestone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pointsNeeded=findViewById(R.id.points_neede_tv);
        reward=findViewById(R.id.reward_tv);
        submitMilestone=findViewById(R.id.submit_milestone);
        mDb=AppDatabase.getsInstance(this);

        submitMilestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInputs())
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.tasksDao().insertMilestone(new Milestones(Integer.parseInt(pointsNeeded.getText().toString()),
                                reward.getText().toString(),0)
                        );
                    }
                }).start();
                onBackPressed();
            }
        });
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
    public boolean validateInputs(){

        if(pointsNeeded.getText().toString().matches(""))
        {
            pointsNeeded.setError(getString(R.string.pointsneedederror));
            return false;

        }
        if(reward.getText().toString().matches(""))
        {
            reward.setError(getString(R.string.rewardneeedederror));
            return false;
        }
        return true;
    }
}
