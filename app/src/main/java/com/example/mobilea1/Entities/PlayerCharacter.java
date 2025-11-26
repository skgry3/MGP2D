package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.health.SystemHealthManager;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class PlayerCharacter extends CharacterEntity {

    private final Bitmap sprite;
    //AnimatedSprite animatedSprite;
    public boolean chosen = false;


    public void onCreate()
    {
        System.out.println(this.name);
    }
    public PlayerCharacter(Vector2 characterSize, int id)
    {
        size = characterSize;
        ID = id;

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);
    }
    @Override
    public void onUpdate(float dt)
    {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.drawBitmap(sprite, _renderPosition.x, _renderPosition.y, null);
    }

}
