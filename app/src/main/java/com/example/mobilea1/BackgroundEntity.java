package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;

public class BackgroundEntity extends GameEntity {

    private final Bitmap _backgroundBitmap0;
    private final Bitmap _backgroundBitmap1;
    private float _backgroundPosition;
    private final float _screenWidth;
    public BackgroundEntity() {
        int screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        _screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.illegelhowchien);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp, (int) _screenWidth, screenHeight, true);
        _backgroundBitmap1 = Bitmap.createScaledBitmap(bmp, (int) _screenWidth, screenHeight, true);
    }
    @Override
    public void onUpdate(float dt)
    {
        _backgroundPosition = (_backgroundPosition - dt * 500f) % _screenWidth;
    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.drawBitmap(_backgroundBitmap0, _backgroundPosition, 0, null);
        canvas.drawBitmap(_backgroundBitmap1, _backgroundPosition + _screenWidth, 0, null);
    }
}

