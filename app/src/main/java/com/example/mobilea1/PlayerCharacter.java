package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class PlayerCharacter extends CharacterEntity {

    private final Bitmap sprite;
    //AnimatedSprite animatedSprite;
    public void onCreate()
    {
        System.out.println(this.name);
    }
    public PlayerCharacter()
    {
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        sprite = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 1.5f), (int) (bmp.getHeight() * 1.5f), true);
    }

    @Override
    public void onUpdate(float dt) {
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, _position.x, _position.y, null);
    }

}
