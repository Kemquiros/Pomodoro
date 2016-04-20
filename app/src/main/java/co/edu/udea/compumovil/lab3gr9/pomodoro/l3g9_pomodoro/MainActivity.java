package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private TextView timestampText;
    private Intent intent;
    private PomodoroService mPomodoroService;
    private boolean mServiceBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timestampText = (TextView) findViewById(R.id.textView_timestamp);
        timestampText.setText(00 + ":" + 00 + ":" + 00 + ":" + 000);

        intent = new Intent(MainActivity.this,
                PomodoroService.class);
        Log.d(TAG,"Creo intent de pomodoro");
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnPrintTimestamp:
                Log.d(TAG, "PrintTimestamp button");
                //startService(intent);

                if (mServiceBound)
                    timestampText.setText(mPomodoroService.getTimestamp());
                    Log.d(TAG, "******TIEMPO: "+mPomodoroService.getTimestamp());



                break;

            case R.id.btnBoundService:
                Log.d(TAG, "BoundService button");
                mServiceBound= true;
                bindService(intent, mServiceConnection, this.BIND_AUTO_CREATE);
                Log.d("Nada","Aqui despues del bind");

               // Intent receiver = new Intent(this, PomodoroReceiver.class);
                //PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, receiver, 0);



                break;

            case R.id.btnUnboundService:
                Log.d(TAG, "UnboundService button");
                Log.d(TAG, "******ACABA: "+mPomodoroService.getTimestamp());

                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                stopService(intent);
                break;
        }

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TAG","Terminé el servicio wey");

            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PomodoroService.MyBinder myBinder = (PomodoroService.MyBinder) service;
            Log.d(TAG,"Conectando el servicio");
            mPomodoroService = myBinder.getService();
            mServiceBound = true;

            try {
                pomodoreando();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void pomodoreando() throws InterruptedException {
            try {
                mPomodoroService.pomodoro(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                mPomodoroService.pomodoroStop(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


}
