package czarnecki.michal.androidtimertest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

import czarnecki.michal.androidtimertest.Activities.BrActiv;
import czarnecki.michal.androidtimertest.tools.TimeConverter;

public class CountdownService extends Service {

    private int counter;
    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            counter++;
            sendMessageToActivity();
        }
    };

    private void sendMessageToActivity() {
        Intent intent = new Intent(BrActiv.TAG);
        intent.putExtra(BrActiv.MESSAGE, TimeConverter.timeInString(counter * 1000));
        sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        counter = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerTask.cancel();
        timer.purge();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
