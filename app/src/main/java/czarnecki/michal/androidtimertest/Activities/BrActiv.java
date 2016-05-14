package czarnecki.michal.androidtimertest.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import czarnecki.michal.androidtimertest.R;
import czarnecki.michal.androidtimertest.Service.CountdownService;
import czarnecki.michal.androidtimertest.models.MyReceiver;

//TODO: add stoping counting
public class BrActiv extends AppCompatActivity {

    public static final String TAG = "czarnecki.michal.androidtimertest.newMsg";
    public static final String MESSAGE = "message";

    private TextView timer;
    private MyReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_br);

        timer = (TextView) findViewById(R.id.brTV);
        initService();
        initReceiver();
 
    }

    private void initReceiver() {
        myReceiver = new MyReceiver(timer, TAG, MESSAGE);
        IntentFilter filter = new IntentFilter(TAG);
        registerReceiver(myReceiver, filter);
    }

    private void initService() {
        Intent intent = new Intent(this, CountdownService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        finishService();
        finishReceiver();
        super.onDestroy();
    }

    private void finishService() {
        Intent intent = new Intent(this, BrActiv.class);
        stopService(intent);
    }

    private void finishReceiver() {
        unregisterReceiver(myReceiver);
    }




}
