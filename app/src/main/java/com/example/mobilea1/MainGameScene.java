package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameScene;

public class MainGameScene extends GameScene {

    private Bitmap backgroundBitmap;
    int screenHeight;
    int screenWidth;

    @Override
    public void onCreate()
    {
        super.onCreate();
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.illegelhowchien);
        backgroundBitmap = Bitmap.createScaledBitmap(bmp, screenWidth, screenHeight, true);
    }


    @Override
    public void onUpdate(float dt)
    {

    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.drawBitmap(backgroundBitmap, 0,0,null);
    }
}
