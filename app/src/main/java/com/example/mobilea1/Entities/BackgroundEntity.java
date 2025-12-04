package com.example.mobilea1.Entities;

import static com.example.mobilea1.Scenes.MainGameScene.mapSize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class BackgroundEntity extends GameEntity {
    private final Bitmap _backgroundBitmap0;
    public BackgroundEntity(Vector2 Size, int index) {
        size = Size;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.dirt);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);


        _position = new Vector2((index-1) * size.x,mapSize.y * 0.5f - size.y * 0.25f);
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

