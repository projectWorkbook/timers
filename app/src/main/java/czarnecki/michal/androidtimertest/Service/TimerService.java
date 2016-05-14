package czarnecki.michal.androidtimertest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import czarnecki.michal.androidtimertest.tools.TimeConverter;

public class TimerService extends Service {

    private IBinder myBinder = new MyBinder();
    private Chronometer chronometer;

    private long timeWhenStopped = 0L;

    private boolean works;

    public TimerService() {
    }

    @Override
    public void onCreate(){
        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        works = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chronometer.stop();

    }

    public String getTimestamp() {
        long elapsedMillis = SystemClock.elapsedRealtime()
                - chronometer.getBase();

        return TimeConverter.timeInString(elapsedMillis);
    }

    public void setTextViewToModify(TextView tv){
        if(works) {
            tv.setText(getTimestamp());
        }
    }

    public void pause(){
        timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
        works = false;
    }

    public void continueCounting(){
        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chronometer.start();
        works = true;
    }

    public class MyBinder extends Binder{
        public TimerService getService(){
            return TimerService.this;
        }
    }


}
