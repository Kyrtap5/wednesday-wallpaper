package de.kyrtap5.wednesdaywallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;

import java.io.IOException;

public class BackgroundChanger {
    private Activity activity;

    public BackgroundChanger(Activity activity) {
        this.activity = activity;
    }
    @SuppressLint("ResourceType")
    public void changeBackground(int resource) {
        WallpaperManager manager = WallpaperManager.getInstance(activity.getApplicationContext());
        try {
            manager.setResource(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
