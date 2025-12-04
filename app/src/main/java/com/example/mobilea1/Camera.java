package com.example.mobilea1;

import android.graphics.Canvas;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Camera extends GameEntity
{
    public enum CamMode {
        FOLLOW_PLAYER,
        FOLLOW_PROJECTILE,
        SNAP_TO_TARGET,
        RETURN_TO_PLAYER
    }
    private static CamMode mode = CamMode.FOLLOW_PLAYER;
    private GameEntity projectileTarget = null;
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
    public void setMode(CamMode Mode)
    {
        mode = Mode;
    }
    public void setTarget(CharacterEntity current) {
        target = current;
    }
    public static float getCamHeight()
    {
        return camHeight;
    }
    public void moveCam(Vector2 targetPos)
    {
        _position = targetPos;
    }
    public void zoomIn()
    {
        camHeight -= 1;
    }
    public void zoomOut()
    {
        camHeight += 1;
    }
    private void smoothFollow(Vector2 targetPos, float dt) {

//        Vector2 distance = new Vector2(targetPos.x - _position.x, targetPos.y - _position.y);
//        if (Math.abs(distance.getMagnitude()) < 10f) {
//            _position = target.getPosition();
//            System.out.println(distance);
//            return;
//        }

        _position.x = _position.x - dt * (targetPos.x - _position.x);
        _position.y = _position.y + dt * (targetPos.y - _position.y);

        System.out.println(targetPos.x - _position.x);
        System.out.println(targetPos.x);
//        _position = targetPos;
    }

    @Override
    public void onUpdate(float dt) {
        if (target == null) return;

        Vector2 centerOffset = new Vector2(screenWidth * 0.5f, screenHeight * 0.5f);
        Vector2 targetPos;

        switch(mode) {

            case FOLLOW_PLAYER:
                targetPos = target.getPosition();
                //smoothFollow(targetPos, dt);
                moveCam(targetPos);
                break;

//            case FOLLOW_PROJECTILE:
//                if (projectileTarget != null && projectileTarget.active) {
//                    targetPos = projectileTarget.getPosition();
//                    smoothFollow(targetPos, dt);
//                } else {
//                    mode = CamMode.RETURN_TO_PLAYER;
//                }
//                break;
//
//            case RETURN_TO_PLAYER:
//                targetPos = target.getPosition();
//                smoothFollow(targetPos, dt);
//
//                // Snap when close enough
//                float distance1 = _position.getMagnitude() - targetPos.getMagnitude();
//
//                if (distance1 < 20f)
//                    mode = CamMode.FOLLOW_PLAYER;
//
//                break;
//
//            case SNAP_TO_TARGET:
//                _position = target.getPosition().copy();
//                mode = CamMode.FOLLOW_PLAYER;
//                break;
        }

        // Apply offset to center camera
        _position.x -= centerOffset.x;
        _position.y -= centerOffset.y;

        xOffset = _position.x;
        yOffset = _position.y;
    }

    @Override
    public void onRender(Canvas canvas) {
    }
}
