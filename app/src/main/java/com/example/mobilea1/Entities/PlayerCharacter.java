package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.Camera;
import com.example.mobilea1.CameraManager;
import com.example.mobilea1.GameManager;
import com.example.mobilea1.Inputs.InputManager;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class PlayerCharacter extends CharacterEntity {
    InputManager im;
    GameManager gm;
    private final Bitmap sprite;
    //AnimatedSprite animatedSprite;

    public void onCreate()
    {
        super.onCreate();
    }
    public PlayerCharacter(Vector2 characterSize, int id, String Name, Vector2 spawnPos, GameManager GM)
    {
        isPlayer = true;
        size = characterSize;
        ID = id;
        name = Name;
        _position = spawnPos;
        gm = GM;
        im = InputManager.getInstance();

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);
    }
    public void actions(float dt) {
        if (im.getSwitchButton().toggled) //fire mode
        {
            showWeapons = true;
            float turnSpeed = 0.1f;
            float facingAngle = getAimAngle();

            stopMovement();
            setMovementDir(new Vector2(0, 0));
            if (im.getJoystick().isPressed()) {

                CameraManager.getInstance().getMainCam().setMode(Camera.CamMode.FOLLOW_PLAYER);
                float inputX = im.getJoystick().actuatorValues.x;

                setAimAngle(facingAngle - inputX * turnSpeed * dt);

                // Wrap angle
                if (facingAngle > Math.PI) {
                    setAimAngle(facingAngle - (float) (2 * Math.PI));
                }
                if (facingAngle < -Math.PI) {
                    setAimAngle(facingAngle + (float) (2 * Math.PI));
                }

                // Convert angle -> direction
                setAimDir(new Vector2((float) Math.cos(facingAngle), (float) Math.sin(facingAngle)));

            }
            if (im.getJumpButton().isPressed(im.getJumpButton().pointerID)) {
                getWeapon(0).Shoot(gm.getGameEntities());

                gm.getTBSInstance().actionCompleted(false);
            }
        } else //move mode
        {
            setMovementDir(new Vector2(im.getJoystick().actuatorValues.x, 0));

            if (im.getJoystick().isPressed()) {
                CameraManager.getInstance().getMainCam().setMode(Camera.CamMode.FOLLOW_PLAYER);
            }

            if (im.getJumpButton().isPressed(im.getJumpButton().pointerID) && onGround) {
                Jump();
            }
        }
    }

    @Override
    public void onTurnStart() {
        im.getSwitchButton().toggled = false;
        super.onTurnStart();
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
        super.onRender(canvas);
    }
}
