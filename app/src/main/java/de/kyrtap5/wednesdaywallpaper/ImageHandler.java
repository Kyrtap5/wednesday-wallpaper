package de.kyrtap5.wednesdaywallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ImageHandler {
    private Context context;

    public ImageHandler(Context context) {
        this.context = context;
    }

    //Save a bitmap to internal storage
    public void saveBitmap(Bitmap bMap, String directoryName, String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(context.getDir(directoryName, Context.MODE_PRIVATE), fileName));
            bMap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Save a drawable to internal storage
    public void saveDrawable(Drawable drawable, String directoryName, String fileName) {
        saveBitmap(((BitmapDrawable) drawable).getBitmap(), directoryName, fileName);
    }

    //Load up a bitmap from internal storage
    public Bitmap loadBitmap(String directoryName, String fileName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(context.getDir(directoryName, Context.MODE_PRIVATE), fileName));
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Load up a drawable from internal storage
    public Drawable loadDrawable(String directoryName, String fileName) {
        return new BitmapDrawable(context.getResources(), loadBitmap(directoryName, fileName));
    }
}
