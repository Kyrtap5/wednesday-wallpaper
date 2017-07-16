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
        //It's wednesday my dudes: change the wallpaper
        if (Week.checkWeekday(Weekday.WEDNESDAY)) bChanger.changeBackground(R.drawable.wednesday);
        else if (Week.checkWeekday(Weekday.THURSDAY)){
            //Change to old wallpaper on Thursday
            bChanger.changeBackground(iHandler.loadBitmap("images", "wallpaper.png"));
        } else {
            //Save the current wallpaper
            iHandler.saveDrawable(bChanger.getBackground(), "images", "wallpaper.png");
        }
    }
}
