package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Button extends GameEntity
{
    private final Bitmap UnpressedSprite;
    private final Bitmap PressedSprite;
    private boolean Pressed = false;
    private int radius;
    Button(Vector2 position,int Radius)
    {
       _position = position;
       radius = Radius;

        Bitmap bmpPressed = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kingsley);
        Bitmap bmpUnpressed = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kingsleypeach);
        PressedSprite = Bitmap.createScaledBitmap(bmpPressed, radius * 2, radius * 2, true);
        UnpressedSprite = Bitmap.createScaledBitmap(bmpUnpressed, radius * 2, radius * 2, true);
    }
    public void setPressed(boolean pressed)
    {
        Pressed = pressed;
    }
    public boolean isPressed()
    {
        return Pressed;
    }
    public boolean contains(float x, float y) {
        float dx = x - _position.x;
        float dy = y - _position.y;
        return dx*dx + dy*dy <= radius*radius;
    }

    @Override
    public void onUpdate(float dt) {
        return;
    }

    @Override
    public void onRender(Canvas canvas)
    {
        if(Pressed)
        {
            canvas.drawBitmap(PressedSprite, _position.x - radius * 2, _position.y - radius * 2 , null);
        }
        else
        {
            canvas.drawBitmap(UnpressedSprite, _position.x - radius * 2, _position.y - radius *2, null);
        }
    }
}
