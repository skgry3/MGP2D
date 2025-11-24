package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class EnemyCharacter extends CharacterEntity {

    private final Bitmap _characterBitmap;
    public final float _screenWidth;

    public void onCreate()
    {
        System.out.println(this.name);
    }
    public EnemyCharacter()
    {
        float sizeRelativeToScreen = 0.1f;

        _screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;

        int size = (int) (_screenWidth * sizeRelativeToScreen);

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        _characterBitmap = Bitmap.createScaledBitmap(bmp, size, size, true);
        _position = new Vector2(_screenWidth,0);
    }

    @Override
    public void onUpdate(float dt) {
        _position.x = (_position.x + (dt * moveSpeed)) % _screenWidth;
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_characterBitmap, _position.x, _position.y, null);
    }

}
