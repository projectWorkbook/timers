package czarnecki.michal.androidtimertest.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import czarnecki.michal.androidtimertest.R;
import czarnecki.michal.androidtimertest.Service.CountdownService;
import czarnecki.michal.androidtimertest.Service.TimerService;
import czarnecki.michal.androidtimertest.tools.TimeConverter;

public class BackgroundTimerActivity extends AppCompatActivity {

    private TimerService myService;
    private boolean myServiceBound = false;
    private TextView timeOnWork;
    private Updater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_timer);

        timeOnWork = (TextView) findViewById(R.id.timeOnWork);

        final Button startOrPauseTimer = (Button) findViewById(R.id.print_timestamp);
        final Button stopServiceButon = (Button) findViewById(R.id.stop_service);
        stopServiceButon.setEnabled(false);

        startService(new Intent(this, CountdownService.class));

        startOrPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(startOrPauseTimer.getText().equals("Zacznij pracę")){
                    Intent intent = new Intent(getApplicationContext(), TimerService.class);
                    startService(intent);
                    bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                    startOrPauseTimer.setText("Pauza");
                    stopServiceButon.setEnabled(true);



                  //  updater.start();

                }
                else if(startOrPauseTimer.getText().equals("Pauza")){
                    if(myServiceBound){
                        myService.pause();
                        startOrPauseTimer.setText("Wznów");
                    }
                }
                else{
                    if(myServiceBound){
                        myService.continueCounting();
                        startOrPauseTimer.setText("Pauza");

                    }
                }
            }


        });

        stopServiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeOnWork.setText(myService.getTimestamp());
                startOrPauseTimer.setText("Zacznij pracę");
                unbindService(mServiceConnection);
                myServiceBound = false;

                Intent intent = new Intent(BackgroundTimerActivity.this,
                        TimerService.class);
                stopService(intent);
                stopServiceButon.setEnabled(false);
                updater.interrupt();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myServiceBound) {
            unbindService(mServiceConnection);
            myServiceBound = false;
        }
    }

    @Override
    public void onBackPressed(){
        myService.pause();
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.MyBinder myBinder = (TimerService.MyBinder) service;
            myService = myBinder.getService();
            myServiceBound = true;
            myService.setTextViewToModify(timeOnWork);
            updater = new Updater(timeOnWork, myService);
            updater.start();


        }
    };

    private class Updater extends Thread
    {
        TextView tv;
        TimerService timerService;

        public Updater(TextView tv, TimerService timerService){
            this.tv = tv;
            this.timerService = timerService;
        }

        public void update(){
            timerService.setTextViewToModify(tv);
        }

        @Override
        public void run(){
                    try {
                        while (myServiceBound) {
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   myService.setTextViewToModify(tv);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            timeOnWork.setText(TimeConverter.timeInMinutesAndSecsonds(millisUntilFinished));
        }
    }
}
