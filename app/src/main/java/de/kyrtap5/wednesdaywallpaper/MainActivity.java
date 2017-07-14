package de.kyrtap5.wednesdaywallpaper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {
    private SharedPreferences sPrefs = null;
    BackgroundChanger bChanger;
    ImageHandler iHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bChanger = new BackgroundChanger(this);
        iHandler = new ImageHandler(this.getApplicationContext());
        if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        //Initialize SharedPreferences
        sPrefs = getSharedPreferences("de.kyrtap5.wednesdaywallpaper", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFirstRun();
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
}
