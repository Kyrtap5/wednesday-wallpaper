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
        bChanger = new BackgroundChanger(this);
        iHandler = new ImageHandler(this);
        //Initialize SharedPreferences
        sPrefs = this.getSharedPreferences("de.kyrtap5.wednesdaywallpaper", MODE_PRIVATE);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        midnightIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        bootIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, BootBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        cLayout = (ConstraintLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sPrefs.getBoolean("active", false)) iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menuSwitch);
        Switch mainSwitch = (Switch) item.getActionView();
        if (sPrefs.getBoolean("active", false)) {
            mainSwitch.setChecked(true);
            if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        } else {
            mainSwitch.setChecked(false);
            if (!Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
        }
        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setupAlarm();
                    sPrefs.edit().putBoolean("active", true).commit();
                    Snackbar.make(cLayout, R.string.active, Snackbar.LENGTH_SHORT).show();
                    if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
                } else {
                    cancelAlarm();
                    sPrefs.edit().putBoolean("active", false).commit();
                    Snackbar.make(cLayout, R.string.disabled, Snackbar.LENGTH_SHORT).show();
                    if (!Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
                }
            }
        });
        return true;
    }

    private void setupAlarm() {
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, midnightIntent);
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, bootIntent);
    }

    private void cancelAlarm() {
        aManager.cancel(midnightIntent);
        aManager.cancel(bootIntent);
    }
}
