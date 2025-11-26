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
    public boolean chosen = false;
    private Vector2 moveDir = new Vector2(0,0);

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
    public void setMovementDir(Vector2 dir)
    {
        moveDir = dir;
        moveDir.y = 0;
    }
    public void Jump()
    {
        AddForce(jumpHeight, new Vector2(0,-1), ForceMode.Impulse);
    }
    @Override
    public void onUpdate(float dt)
    {
        AddForce(moveSpeed, moveDir, ForceMode.Force);

        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.drawBitmap(sprite, _renderPosition.x, _renderPosition.y, null);
    }

}
