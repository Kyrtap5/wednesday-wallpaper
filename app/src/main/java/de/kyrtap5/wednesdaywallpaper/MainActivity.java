package de.kyrtap5.wednesdaywallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: Implement weekday check
        BackgroundChanger bChanger = new BackgroundChanger(this);
        bChanger.changeBackground(R.drawable.wednesday);
    }


}
