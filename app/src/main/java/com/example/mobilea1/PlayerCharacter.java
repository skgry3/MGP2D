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
    public PlayerCharacter(int size)
    {
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        sprite = Bitmap.createScaledBitmap(bmp, size, size, true);
    }
    public void setMovementDir(Vector2 dir)
    {
        moveDir = dir;
    }

    @Override
    public void onUpdate(float dt)
    {
        if(chosen)
        {
            _position.x -= moveDir.x * moveSpeed;
            _position.y -= moveDir.y * moveSpeed;

            _position.x = Math.clamp(_position.x, 0, GameActivity.instance.getResources().getDisplayMetrics().widthPixels - getSize().x );
            _position.y = Math.clamp(_position.y, 0, GameActivity.instance.getResources().getDisplayMetrics().heightPixels - getSize().y );
        }
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, _position.x - getSize().x, _position.y - getSize().y, null);
    }

}
