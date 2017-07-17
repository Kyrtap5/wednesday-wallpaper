package de.kyrtap5.wednesdaywallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sPrefs = null;
    private BackgroundChanger bChanger;
    private ImageHandler iHandler;
    private AlarmManager aManager;
    private PendingIntent midnightIntent;
    private PendingIntent bootIntent;
    private Calendar calendar;
    private ConstraintLayout cLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize BackgroundChanger and ImageHandler
        bChanger = new BackgroundChanger(this);
        iHandler = new ImageHandler(this);
        //Initialize SharedPreferences
        sPrefs = this.getSharedPreferences("de.kyrtap5.wednesdaywallpaper", MODE_PRIVATE);
        //Create midnightIntent
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        midnightIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //Create bootIntent
        bootIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, BootBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //Initialize AlarmManager
        aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Get activity_main ConstraintLayout
        cLayout = (ConstraintLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //If not active: save the current device wallpaper
        if (!sPrefs.getBoolean("active", false)) iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Get mainSwitch Switch
        MenuItem item = menu.findItem(R.id.menuSwitch);
        Switch mainSwitch = (Switch) item.getActionView();
        //Set mainSwitch to right position depending on active-preference
        if (sPrefs.getBoolean("active", false)) {
            mainSwitch.setChecked(true);
            //It's wednesday my dudes: change the wallpaper
            if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        } else {
            mainSwitch.setChecked(false);
            //Load up the old wallpaper and change it for every other weekday
            if (!Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
        }
        //Add listener to switch
        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Set up AlarmManager
                    setupAlarm();
                    sPrefs.edit().putBoolean("active", true).commit();
                    Snackbar.make(cLayout, R.string.active, Snackbar.LENGTH_SHORT).show();
                    //It's wednesday my dudes: change the wallpaper
                    if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
                } else {
                    //Reset AlarmManager
                    cancelAlarm();
                    sPrefs.edit().putBoolean("active", false).commit();
                    Snackbar.make(cLayout, R.string.disabled, Snackbar.LENGTH_SHORT).show();
                    //Load up the old wallpaper and change it for every other weekday
                    bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
                }
            }
        });
        return true;
    }

    private void setupAlarm() {
        //Check whether it is wednesday when it's midnight or the device has booted
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, midnightIntent);
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, bootIntent);
    }

    private void cancelAlarm() {
        aManager.cancel(midnightIntent);
        aManager.cancel(bootIntent);
    }
}
