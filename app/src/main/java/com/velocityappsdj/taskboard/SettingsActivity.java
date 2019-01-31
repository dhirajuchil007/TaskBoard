package com.velocityappsdj.taskboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    TextView reset;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        reset=findViewById(R.id.reset_all_data);
        mDb=AppDatabase.getsInstance(getApplicationContext());


         reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new AlertDialog.Builder(SettingsActivity.this)
                         .setMessage(getString(R.string.resetall))
                         .setCancelable(false)
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {




                                  new Thread(new Runnable() {
                                      @Override
                                      public void run() {
                                          mDb.tasksDao().nukeMilestones();
                                          mDb.tasksDao().nukeTasks();
                                          mDb.tasksDao().updateTotalPoints(0);

                                      }
                                  }).start();
                                 onBackPressed();


                                 }


                             }
                         )
                         .setNeutralButton("No", null)
                         .show();

             }
         });

    }
}
