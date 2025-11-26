package com.example.mobilea1;

import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Camera extends GameEntity
{
    private static float camHeight;
    private static float xOffset;
    private static float yOffset;
    private static GameEntity target;
    int screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
    int screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
    public static Vector2 getOffset()
    {
        return new Vector2(xOffset, yOffset);
    }
    public static float getCamHeight()
    {
        return camHeight;
    }
    public void moveCam(float xDelta, float yDelta)
    {
        xOffset += xDelta;
        yOffset += yDelta;
    }
    public void zoomIn()
    {
        camHeight -= 1;
    }
    public void zoomOut()
    {
        camHeight += 1;
    }
    public void setTarget(GameEntity Target)
    {
        target = Target;
    }

    public static GameEntity getTarget() {
        return target;
    }

    @Override
    public void onUpdate(float dt) {
        if(target != null)
        {
            _position = target.getPosition();
            _position.x -= screenWidth * 0.5f;
            _position.y -= screenHeight * 0.5f;
            xOffset = _position.x;
            yOffset = _position.y;
        }

    }

    @Override
    public void onRender(Canvas canvas) {
        return;
    }
}
