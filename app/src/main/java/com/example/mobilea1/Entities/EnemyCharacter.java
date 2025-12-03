package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.Combat.WeaponBase;
import com.example.mobilea1.GameManager;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.AnimatedSprite;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class EnemyCharacter extends CharacterEntity {

    private final Bitmap spriteRun;
    private final Bitmap spriteIdle;
    private final Bitmap spriteJumpUp;
    private final Bitmap spriteJumpDown;
    AnimatedSprite animatedSpriteRun;
    AnimatedSprite animatedSpriteIdle;
    AnimatedSprite animatedSpriteJumpUp;
    AnimatedSprite animatedSpriteJumpDown;
    AnimatedSprite chosenAnimation;
    WeaponBase currentWeapon;
    GameManager gm;

    //AnimatedSprite animatedSprite;
    enum STATE
    {
        Idle,
        Moving,
        Attack,
        Done
    }
    public STATE currentState;
    private float moveTime;
    public void onCreate()
    {
        super.onCreate();
    }
    public EnemyCharacter(Vector2 characterSize, int id, String Name, Vector2 spawnPos, GameManager GM)
    {
        isEnemy = true;
        size = characterSize;
        ID = id;
        name = Name;
        _position = spawnPos;
        gm = GM;
        
        currentState = STATE.Idle;

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
    public void runAI(float dt) {
        switch(currentState)
        {
            case Idle:
                currentState = STATE.Moving;
                moveTime = 3f;
                //idle animation
                break;
            case Attack:
                float ranX = (float) Math.random();
                float ranY = (float) Math.random();

                setAimDir(new Vector2(ranX, ranY));
                currentWeapon = _weapons.get(0);
                currentWeapon.Shoot(gm.getGameEntities());
                currentState = STATE.Done;
                break;
            case Moving:
                Vector2 ranMovement = new Vector2((float) (Math.random() * 10f - 5f), 0f);
                setMovementDir(ranMovement);
                if(moveTime <= 0) {
                    currentState = STATE.Attack;
                    stopMovement();
                    setMovementDir(new Vector2(0,0));
                }
                else {
                    moveTime -= dt;
                }
                break;
            case Done:
                gm.getTBSInstance().actionCompleted(false);
                currentState = STATE.Idle;
                break;
        }
    }
    private void changeAnimation(AnimatedSprite animation)
    {
        if(chosenAnimation == animatedSpriteJumpDown)
        {
            if(onGround)
            {
                chosenAnimation = animation;
                return;
            }
        }
        else if(chosenAnimation == animatedSpriteJumpUp)
        {
            if(onGround || animation == animatedSpriteJumpDown)
            {
                chosenAnimation = animation;
                return;
            }
            return;
        }
        else if(chosenAnimation == animatedSpriteRun) {
            if(animation == animatedSpriteJumpUp) {
                chosenAnimation = animation;
            }
            if(animation == animatedSpriteIdle)
            {
                chosenAnimation = animation;
            }
            return;
        }
        else{ //run
            chosenAnimation = animation;
        }
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
        canvas.restore();
        super.onRender(canvas);
    }
}
