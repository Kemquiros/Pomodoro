package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class PomodoroStopReceiver extends BroadcastReceiver {
    public PomodoroStopReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("asd,", "Entre a vibrar");
        Vibrator vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrador.vibrate(2000);
        Toast.makeText(context, "Reanuda la actividad", Toast.LENGTH_LONG).show();
    }
}
