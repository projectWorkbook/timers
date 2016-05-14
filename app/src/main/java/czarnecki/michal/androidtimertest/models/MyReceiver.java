package czarnecki.michal.androidtimertest.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class MyReceiver extends BroadcastReceiver {

    public final String TAG;
    public final String MESSAGE;

    private TextView timerView;
    public MyReceiver(TextView textView, String tag, String message){
        this.timerView = textView;
        this.TAG = tag;
        this.MESSAGE = message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(TAG)){
            String message = intent.getStringExtra(MESSAGE);
            timerView.setText(message);
        }
    }
}
