package czarnecki.michal.androidtimertest.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import czarnecki.michal.androidtimertest.R;
import czarnecki.michal.androidtimertest.tools.TimeConverter;

public class CountdownTimer extends AppCompatActivity {

    private TextView text;
    private MyCountDownTimer countDownTimer;

    private Button countdownController;
    private Button resetCountdown;
    private Button takeTime;
    private Button addTime;

    private final long maxTime = 1800000;
    private long interval = 1000;

    private long remainingTime = maxTime;

    private Handler handler = new Handler();

    public CountdownTimer() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_timer);

        text = (TextView)findViewById(R.id.countdownTimer);

        countdownController = (Button) findViewById(R.id.countdownController);

        countdownController.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                handler.postDelayed(count, 0);
            }        });

        resetCountdown = (Button) findViewById(R.id.resetCountdown);

        resetCountdown.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                handler.postDelayed(reset, 0);

            }
        });

        takeTime = (Button) findViewById(R.id.takeTime);

        takeTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                handler.postDelayed(takeTimeFromCounter, 0);
            }
        });

        addTime = (Button) findViewById(R.id.addTime);

        addTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                handler.postDelayed(addTimeToCounter, 0);
            }
        });

    }

    public class MyCountDownTimer extends CountDownTimer
    {

        public MyCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1500);
            text.setText("00:00");
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            remainingTime = millisUntilFinished;

            text.setText(TimeConverter.timeInMinutesAndSecsonds(remainingTime));
        }
    }

    private Runnable count = new Runnable() {
        @Override
        public void run() {
            if (countdownController.getText().equals("Start")) {

                countDownTimer = new MyCountDownTimer(remainingTime, interval);
                countDownTimer.start();
                countdownController.setText("Pause");


            } else if (countdownController.getText().equals("Pause")) {

                countDownTimer.cancel();
                countdownController.setText("Start");
            }
        }
    };

    private Runnable reset = new Runnable() {
        @Override
        public void run() {
            remainingTime = maxTime;
            countDownTimer.cancel();
            countDownTimer = new MyCountDownTimer(remainingTime, interval);

            text.setText(TimeConverter.timeInMinutesAndSecsonds(remainingTime));

            if (countdownController.getText().equals("Pause")) {
                countDownTimer.start();
            }
        }
    };

    private Runnable addTimeToCounter = new Runnable() {
        @Override
        public void run() {
            remainingTime += 1000 * 60 * 5;
            if(remainingTime > maxTime){
                remainingTime = maxTime;
            }

            countDownTimer.cancel();
            countDownTimer = new MyCountDownTimer(remainingTime, interval);


            text.setText(TimeConverter.timeInMinutesAndSecsonds(remainingTime));

            if (countdownController.getText().equals("Pause")) {
                countDownTimer.start();
            }
        }
    };

    private Runnable takeTimeFromCounter = new Runnable() {
        @Override
        public void run() {

            remainingTime -= 1000 * 60 * 5;

            if(remainingTime < 0 ){
                remainingTime = 0L;
            }

            countDownTimer.cancel();
            countDownTimer = new MyCountDownTimer(remainingTime, interval);

            text.setText(TimeConverter.timeInMinutesAndSecsonds(remainingTime));

            if (countdownController.getText().equals("Pause")) {
                countDownTimer.start();
            }

            if(remainingTime == 0){
                countdownController.setText("Start");
                remainingTime = maxTime;
                text.setText("30:00");
            }
        }
    };
}
