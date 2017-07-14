package de.kyrtap5.wednesdaywallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.IOException;

public class BackgroundChanger {
    private Context context;
    private WallpaperManager manager;

    public BackgroundChanger(Context context) {
        this.context = context;
        manager = WallpaperManager.getInstance(context);
    }

    //Set the device wallpaper to a resource file
    @SuppressLint("ResourceType")
    public void changeBackground(int resource) {
        try {
            manager.setResource(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeBackground(Bitmap bitmap) {
        try {
            manager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get the current device wallpaper
    public Drawable getBackground() {
        return manager.getDrawable();
    }
}
