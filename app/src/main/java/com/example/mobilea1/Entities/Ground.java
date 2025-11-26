package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Ground extends GameEntity
{
    private final Bitmap sprite;
    public Ground(Vector2 groundSize)
    {
        size = groundSize;

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kingsley);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, _renderPosition.x, _renderPosition.y, null);
    }
}
