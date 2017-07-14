package de.kyrtap5.wednesdaywallpaper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends Activity {
    private SharedPreferences sPrefs = null;
    private BackgroundChanger bChanger;
    private ImageHandler iHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bChanger = new BackgroundChanger(this.getApplicationContext());
        iHandler = new ImageHandler(this.getApplicationContext());
        //Initialize SharedPreferences
        sPrefs = getSharedPreferences("de.kyrtap5.wednesdaywallpaper", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFirstRun();
        setupAlarm();
    }

    private boolean checkFirstRun() {
        //Check SharedPreferences for firstrun attribute
        if (sPrefs.getBoolean("firstrun", true)) {
            //App started for the first time: save current wallpaper
            iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
            sPrefs.edit().putBoolean("firstrun", false).commit();
            return true;
        } else return false;
    }

    private void setupAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, new Intent(this.getApplicationContext(), AlarmBroadcastReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
    }
}
