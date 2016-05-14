package czarnecki.michal.androidtimertest.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import czarnecki.michal.androidtimertest.R;

/**
 *  Ręczne wyliczanie czasu
 */
public class TimerActivity extends Activity {

    private Button timerControlButton;
    private Button resetTimeButton;

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    static long  timeInMilliseconds = 0L;
    static long  timeSwapBuff = 0L;
    static long  updatedTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initiateComponents();
        setListeners();

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;

            timerValue.setText("" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };

    private Runnable resetTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = 0L;
            timeSwapBuff = 0L;
            updatedTime = 0L;

            timerValue.setText(R.string.timerVal);

        }
    };

    private void setListeners(){

        timerControlButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (timerControlButton.getText().equals("Start")) {

                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    timerControlButton.setText("Pause");

                } else if (timerControlButton.getText().equals("Pause")) {

                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    timerControlButton.setText("Start");

                }
            }
        });


        resetTimeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                customHandler.removeCallbacks(updateTimerThread);
                customHandler.postDelayed(resetTimerThread, 0);

                if(timerControlButton.getText().equals("Pause")){
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 500);
                }

            }
        });

    }

    private void initiateComponents(){
        timerValue = (TextView) findViewById(R.id.timerValue);
        timerControlButton = (Button) findViewById(R.id.timerControlButton);
        resetTimeButton = (Button) findViewById(R.id.resetTimeButton);

    }


}