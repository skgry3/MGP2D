package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class EnemyCharacter extends CharacterEntity {

    private final Bitmap sprite;
    //AnimatedSprite animatedSprite;
    public boolean chosen = false;
    private Vector2 moveDir = new Vector2(0,0);

    public void onCreate()
    {
        System.out.println(this.name);
    }
    public EnemyCharacter(Vector2 characterSize, int id)
    {
        size = characterSize;
        ID = id;

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin2);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);
    }
    public void setMovementDir(Vector2 dir)
    {
        moveDir = dir;
    }

    @Override
    public void onUpdate(float dt)
    {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, _renderPosition.x, _renderPosition.y, null);
    }

}
