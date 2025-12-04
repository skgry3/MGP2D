package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.GameManager;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class ParallaxBgEntity extends GameEntity {
    private final Bitmap _backgroundBitmap0;
    private final Bitmap _backgroundBitmap1;
    private final Bitmap _backgroundBitmap2;
    private final Bitmap _backgroundBitmap3;
    public ParallaxBgEntity(Vector2 Size,int index) {
        size = Size;
        Bitmap bmp0 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.background1);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp0, (int) size.x, (int) size.y, true);

        Bitmap bmp1 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.background2);
        _backgroundBitmap1 = Bitmap.createScaledBitmap(bmp1, (int) size.x, (int) size.y, true);

        Bitmap bmp2 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.background3);
        _backgroundBitmap2 = Bitmap.createScaledBitmap(bmp2, (int) size.x, (int) size.y, true);

        Bitmap bmp3 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.background4);
        _backgroundBitmap3 = Bitmap.createScaledBitmap(bmp3, (int) size.x, (int) size.y, true);

        _position = new Vector2((index-1) * size.x, 0);
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
        canvas.drawBitmap(_backgroundBitmap1, _renderPosition.x, _renderPosition.y, null);
        canvas.drawBitmap(_backgroundBitmap2, _renderPosition.x, _renderPosition.y, null);
        canvas.drawBitmap(_backgroundBitmap3, _renderPosition.x, _renderPosition.y, null);
    }
}
