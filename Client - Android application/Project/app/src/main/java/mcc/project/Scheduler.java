package mcc.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SUMIT on 5/7/2017.
 */

public class Scheduler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Log.d("Log","Sending 100");
        Log.d("Scheduler", "Alarm !!!!!!!!!!"); // For example
        wl.release();
    }

    public void setAlarm(Context context)
    {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Scheduler.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 15 , pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Scheduler.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
