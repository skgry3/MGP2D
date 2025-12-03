package com.example.mobilea1.Inputs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Joystick extends GameEntity {

    private final Bitmap innerSprite;
    private final Bitmap outerSprite;
    private Vector2 outerCircleCenterPos;
    private Vector2 innerCircleCenterPos;
    private final int outerCircleRadius;
    private final int innerCircleRadius;
    private boolean Pressed = false;
    public Vector2 actuatorValues = new Vector2(0,0);
    public int pointerID;

    public Joystick(Vector2 centerPos, int outerCircleR, int innerCircleR)
    {
        outerCircleCenterPos = centerPos;
        innerCircleCenterPos = centerPos;

        this.outerCircleRadius = outerCircleR;
        this.innerCircleRadius = innerCircleR;

        Bitmap bmpIn = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.unpress);
        Bitmap bmpOut = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.press);
        innerSprite = Bitmap.createScaledBitmap(bmpIn, innerCircleR * 2, innerCircleR *2, true);
        outerSprite = Bitmap.createScaledBitmap(bmpOut, outerCircleR *2, outerCircleR *2, true);

        pointerID = -1;
    }
    public void setPressed(boolean pressed)
    {
        Pressed = pressed;
    }
    public boolean isPressed()
    {
        return Pressed;
    }
    public void setActuator(Vector2 touchPos)
    {
        float deltaX = outerCircleCenterPos.x - touchPos.x;
        float deltaY = outerCircleCenterPos.y - touchPos.y;

        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > outerCircleRadius)
        {
            float normalizedDeltaX = deltaX / distance;
            float normalizedDeltaY = deltaY / distance;

            deltaX = normalizedDeltaX * outerCircleRadius;
            deltaY = normalizedDeltaY * outerCircleRadius;
        }

        actuatorValues = new Vector2(deltaX, deltaY);

        innerCircleCenterPos = new Vector2(_position.x - deltaX, _position.y - deltaY);
    }
    public void resetActuator()
    {
        innerCircleCenterPos = outerCircleCenterPos;
        actuatorValues = new Vector2(0,0);
    }
    public void resetJoystick()
    {
        show = false;
        resetActuator();
        setPressed(false);
        pointerID = -1;
    }
    @Override
    public void onUpdate(float dt) {
        if(Pressed)
        {
            outerCircleCenterPos = _position;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(outerSprite, outerCircleCenterPos.x - outerCircleRadius, outerCircleCenterPos.y - outerCircleRadius, null);
        canvas.drawBitmap(innerSprite, innerCircleCenterPos.x - innerCircleRadius, innerCircleCenterPos.y - innerCircleRadius, null);
    }
}
