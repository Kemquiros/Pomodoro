package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG,"onStartCommand");

        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        Log.d(LOG_TAG, "********COMIENZA: "+this.getTimestamp());
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");

        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        return mBinder;
    }
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }
    public String getTimestamp() {
        Log.v(LOG_TAG,"********ENTRA AL BARRIO**********\n");
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
        Log.v(LOG_TAG,"Devolviendo tiempo transcurrido");
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }
    public class MyBinder extends Binder {
        PomodoroService getService() {
            return PomodoroService.this;
        }
    }
}
