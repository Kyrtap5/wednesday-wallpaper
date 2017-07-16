package de.kyrtap5.wednesdaywallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private BackgroundChanger bChanger;
    private ImageHandler iHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        bChanger = new BackgroundChanger(context);
        iHandler = new ImageHandler(context);
        if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        else if (Week.checkWeekday(Weekday.THURSDAY)){
            bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
        } else {
            iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
        }
    }
}
