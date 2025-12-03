package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class BackgroundEntity extends GameEntity {

    private final Bitmap _backgroundBitmap0;


    public BackgroundEntity(Vector2 Size) {
        size = Size;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.background1);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);


        _position = new Vector2(0,-(size.y * 0.5f));
    }
    @Override
    public void onUpdate(float dt)
    {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.drawBitmap(_backgroundBitmap0, _renderPosition.x, _renderPosition.y, null);

    }
}

