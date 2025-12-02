package com.example.mobilea1.Inputs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Button extends GameEntity
{
    private final Bitmap UnpressedSprite;
    private final Bitmap PressedSprite;
    private boolean Pressed = false;
    public boolean justPressed = false;
    private final int radius;
    public int pointerID;
    public enum TYPE
    {
        MomentaryPush,
        Toggle
    }
    public TYPE type;
    public boolean toggled = false;

    public Button(Vector2 position,int Radius,TYPE Type)
    {
       _position = position;
       radius = Radius;

        Bitmap bmpPressed = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kingsley);
        Bitmap bmpUnpressed = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kingsleypeach);
        PressedSprite = Bitmap.createScaledBitmap(bmpPressed, radius * 2, radius * 2, true);
        UnpressedSprite = Bitmap.createScaledBitmap(bmpUnpressed, radius * 2, radius * 2, true);


        pointerID = -1;
        type = Type;
    }
    public void setPressed(boolean pressed, int PointerID)
    {
        boolean wasPressed = Pressed;
        Pressed = pressed;

        if (pressed)
        {
            pointerID = PointerID;
            justPressed = !wasPressed;
        }
        else
        {
            pointerID = -1;
            justPressed = false;
        }
    }
    public boolean isPressed(int PointerID)
    {
        return Pressed && pointerID == PointerID;
    }
    public void Unpressed(int PointerID)
    {
        if (pointerID != PointerID)
            return;

        Pressed = false;
        pointerID = -1;
    }
    public void reset()
    {
        Pressed = false;
        pointerID = -1;
    }
    public boolean contains(float x, float y) {
        float dx = x - _position.x;
        float dy = y - _position.y;
        return dx*dx + dy*dy <= radius*radius;
    }

    @Override
    public void onUpdate(float dt) {
    }

    @Override
    public void onRender(Canvas canvas)
    {
        Bitmap spriteToDraw;

        if (type == TYPE.MomentaryPush)
        {
            spriteToDraw = Pressed ? PressedSprite : UnpressedSprite;
        }
        else  // TYPE.Toggle
        {
            spriteToDraw = toggled ? UnpressedSprite : PressedSprite;
        }

        canvas.drawBitmap(spriteToDraw, _position.x - radius * 2, _position.y - radius * 2, null);
    }
}
