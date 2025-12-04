package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import androidx.dynamicanimation.animation.SpringAnimation;

import com.example.mobilea1.Camera;
import com.example.mobilea1.CameraManager;
import com.example.mobilea1.GameManager;
import com.example.mobilea1.Inputs.InputManager;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.AnimatedSprite;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class PlayerCharacter extends CharacterEntity {
    InputManager im;
    GameManager gm;
    private final Bitmap sprite;
    private final Bitmap spriteRun;
    private final Bitmap spriteIdle;
    private final Bitmap spriteJumpUp;
    private final Bitmap spriteJumpDown;
    AnimatedSprite animatedSpriteRun;
    AnimatedSprite animatedSpriteIdle;
    AnimatedSprite animatedSpriteJumpUp;
    AnimatedSprite animatedSpriteJumpDown;
    AnimatedSprite chosenAnimation;

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
        Bitmap bmp  = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);

        Bitmap bmp0 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.run);
        spriteRun = Bitmap.createScaledBitmap(bmp0, (int) size.x * 9, (int) size.y, true);
        animatedSpriteRun = new AnimatedSprite(spriteRun, 1, 9, 24);

        Bitmap bmp1 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.idle);
        spriteIdle = Bitmap.createScaledBitmap(bmp1, (int) size.x * 6, (int) size.y, true);
        animatedSpriteIdle = new AnimatedSprite(spriteIdle, 1, 6, 24);

        Bitmap bmp2 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.jumpup);
        spriteJumpUp = Bitmap.createScaledBitmap(bmp2, (int) size.x, (int) size.y, true);
        animatedSpriteJumpUp = new AnimatedSprite(spriteJumpUp, 1, 1, 24);

        Bitmap bmp3 = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.jumpdown);
        spriteJumpDown = Bitmap.createScaledBitmap(bmp3, (int) size.x, (int) size.y, true);
        animatedSpriteJumpDown = new AnimatedSprite(spriteJumpDown, 1, 1, 24);

        chosenAnimation = animatedSpriteIdle;
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
                changeAnimation(animatedSpriteJumpUp);
            }
        }
    }
    private void changeAnimation(AnimatedSprite animation) {
        if (chosenAnimation == animatedSpriteJumpDown) {
            if (onGround) {
                chosenAnimation = animation;
            }
        } else if (chosenAnimation == animatedSpriteJumpUp) {
            if (onGround || animation == animatedSpriteJumpDown) {
                chosenAnimation = animation;
            }
        } else if (chosenAnimation == animatedSpriteRun) {
            if (animation == animatedSpriteJumpUp) {
                chosenAnimation = animation;
            }
            if (animation == animatedSpriteIdle) {
                chosenAnimation = animation;
            }
        } else { //run
            chosenAnimation = animation;
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
        if(VerticalVel > 0 && !onGround)
        {
            changeAnimation(animatedSpriteJumpDown);
        }
        else if(Math.abs(HorizontalVel) > 1){
            changeAnimation(animatedSpriteRun);
        }
        else {
            changeAnimation(animatedSpriteIdle);
        }
        chosenAnimation.update(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        canvas.save();
        canvas.scale(flip, 1, _renderPosition.x, _renderPosition.y);
        chosenAnimation.render(canvas, (int) _renderPosition.x, (int) _renderPosition.y, null);
        //canvas.drawBitmap(sprite,_renderPosition.x, _renderPosition.y, null);
        canvas.restore();

        super.onRender(canvas);
    }
}
