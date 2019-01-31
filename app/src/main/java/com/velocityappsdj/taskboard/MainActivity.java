package com.velocityappsdj.taskboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClicked {
    AppDatabase mDb;
    RecyclerView taskList;
    private AdView mAdView;
    List<Tasks> t;
    TextView noTask;
    TextView milestoneLable, milestoneTextView,totalPointsLabel, totalPointsTextView,reward,addMilestoneIns;
    CardView mileStoneCard;
    public static final String POINTS_REACHED="pointsreached";
    public static final String REWARD_EARNED="rewaredearned";
    public static final String TASK_ID="taskid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskList=findViewById(R.id.task_list);
        noTask=findViewById(R.id.no_task_tv);
        mDb=AppDatabase.getsInstance(getApplicationContext());
        milestoneLable=findViewById(R.id.next_milestone_tv);
        milestoneTextView =findViewById(R.id.points_next_reward_tv);
        totalPointsLabel=findViewById(R.id.total_points_label_tv);
        totalPointsTextView =findViewById(R.id.total_points_tv);
        addMilestoneIns=findViewById(R.id.add_milestone_instruction_tv);
        reward=findViewById(R.id.reward_tv);

        MobileAds.initialize(this, getString(R.string.admobappid));
        mAdView = findViewById(R.id.adView);

        mileStoneCard=findViewById(R.id.cardView2);
        mileStoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MilestonesActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);

            }
        });




        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            databaseSetup();

            Intent intent=new Intent(MainActivity.this,OnBoard.class);
            startActivity(intent);




            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        getData();

        initBannerAds();
    }
    public void databaseSetup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TotalPoints totalPoints=new TotalPoints(0);
                mDb.tasksDao().insertTotalPoints(totalPoints);
            }
        }).start();
    }

    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                 t=mDb.tasksDao().loadAllTasks();
                 final List<TotalPoints> totalPoints=mDb.tasksDao().getTotalPoints();

           //      final int total=totalPoints.get(0).points;

                 final List<Milestones> milestones=mDb.tasksDao().getNextMileStone();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(milestones.size()==0)
                        {
                            hideCardContents();
                        }
                        else {
                            showCardContents();
                            Milestones  m=milestones.get(0);

                            milestoneTextView.setText(String.valueOf(m.totalPoints));
                            reward.setText(getString(R.string.reward)+m.reward);

                        }
                        if(totalPoints.size()!=0)
                        totalPointsTextView.setText(String.valueOf(totalPoints.get(0).points));
                        if(t.size()==0)
                        {
                            noTask.setVisibility(View.VISIBLE);
                            taskList.setAdapter(null);
                            return;
                        }

                        taskList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        TaskAdapter taskAdapter=new TaskAdapter(t);
                        taskList.setAdapter(taskAdapter);
                        taskAdapter.setOnClick(MainActivity.this);
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
                                        Tasks currentTask=mDb.tasksDao().getTaskFromId(id);
                                        Log.d("IDIS", "run: "+id);
                                        int total=mDb.tasksDao().getTotalPoints().get(0).points;
                                        mDb.tasksDao().updateTotalPoints(currentTask.priority+total);
                                        List<Milestones> completedMilestones=mDb.tasksDao().getCompletedMilestones();
                                        mDb.tasksDao().updateCompletedMilestone();
                                        if(completedMilestones.size()>0)
                                        {
                                           // displayCompletedScreen(completedMilestones);
                                            Intent intent=new Intent(MainActivity.this,completedMilestone.class);
                                            intent.putExtra(POINTS_REACHED,completedMilestones.get(0).totalPoints);
                                            intent.putExtra(REWARD_EARNED,completedMilestones.get(0).reward);
                                            startActivity(intent);
                                        }

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
    public void hideCardContents(){
       // totalPointsTextView.setVisibility(View.INVISIBLE);
      //  totalPointsLabel.setVisibility(View.INVISIBLE);
        milestoneLable.setVisibility(View.INVISIBLE);
        milestoneTextView.setVisibility(View.INVISIBLE);
        reward.setVisibility(View.INVISIBLE);
        addMilestoneIns.setVisibility(View.VISIBLE);

    }

    public void showCardContents(){
      //  totalPointsTextView.setVisibility(View.VISIBLE);
       // totalPointsLabel.setVisibility(View.VISIBLE);
        milestoneLable.setVisibility(View.VISIBLE);
        milestoneTextView.setVisibility(View.VISIBLE);
        reward.setVisibility(View.VISIBLE);
        addMilestoneIns.setVisibility(View.INVISIBLE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_completed_tasks)
        {
            Intent intent=new Intent(MainActivity.this,CompletedTasks.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void initBannerAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public void onItemClick(int position) {

        Intent intent=new Intent(MainActivity.this,AddTask.class);
        intent.putExtra(TASK_ID,t.get(position).id);
        startActivity(intent);

    }
}

