package de.kyrtap5.wednesdaywallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private BackgroundChanger bChanger;
    private ImageHandler iHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        bChanger = new BackgroundChanger(context);
        iHandler = new ImageHandler(context);
        if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        else if (Week.checkWeekday(Weekday.THURSDAY)){
            Bitmap bMap = iHandler.loadBitmap("images", "wallpaper.png");
            bChanger.changeBackground(bMap);
        } else {
            iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
        }
    }
}
