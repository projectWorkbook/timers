package czarnecki.michal.androidtimertest.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import czarnecki.michal.androidtimertest.R;

public class MainActivity extends AppCompatActivity {

    private Button stoper;
    private Button counter;
    private Button service;
    private Button broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initiateComponents();
        setListeners();
    }

    private void initiateComponents(){
        stoper = (Button) findViewById(R.id.stoper);
        counter = (Button) findViewById(R.id.counter);
        service = (Button) findViewById(R.id.service);
        broadcast = (Button) findViewById(R.id.broadcastButton);
    }

    private void setListeners(){

        stoper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TimerActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CountdownTimer.class);
                MainActivity.this.startActivity(i);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BackgroundTimerActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BrActiv.class);
                MainActivity.this.startActivity(i);
            }
        });
    }
}
