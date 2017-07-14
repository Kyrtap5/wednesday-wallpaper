package de.kyrtap5.wednesdaywallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;

public class BackgroundChanger {
    private Context context;
    private WallpaperManager manager;

    public BackgroundChanger(Context context) {
        this.context = context;
        manager = WallpaperManager.getInstance(context);
    }

    @SuppressLint("ResourceType")
    public void changeBackground(int resource) {
        try {
            manager.setResource(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable getBackground() {
        return manager.getDrawable();
    }
}
