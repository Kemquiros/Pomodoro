package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//RealG4Life
public class PomodoroService extends Service {
    private static String LOG_TAG = "PomodoroService";
    private IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");

    }
    public void onDestroy() {
        Toast.makeText(this, "Servicio detenido",
                Toast.LENGTH_SHORT).show();
        mChronometer.stop();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG, "onStartCommand");
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        return mBinder;
    }
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        onDestroy();
        return true;
    }
    public String getTimestamp() {
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);

        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }
    public void pomodoro(int time) throws InterruptedException {
        mChronometer.setBase(0);
        for(int i = 0 ; i<time;i++) {
            Thread.sleep(1000);
            Log.d("Cron", Integer.toString(i));
            mChronometer.setBase(mChronometer.getBase()+1000);
        }
        Intent i = new Intent(this,PomodoroReceiver.class);
        sendBroadcast(i);
    }


    public void pomodoroStop(int time) throws InterruptedException {
        mChronometer.setBase(0);
        for(int i = 0 ; i<time;i++) {
            Thread.sleep(1000);
            Log.d("Cron", Integer.toString(i));
            mChronometer.setBase(mChronometer.getBase()+1000);
        }
        Intent i2 = new Intent(this,PomodoroStopReceiver.class);
        sendBroadcast(i2);
    }
    public class MyBinder extends Binder {
        PomodoroService getService() {
            return PomodoroService.this;
        }
    }

}
