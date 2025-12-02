package com.example.mobilea1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//import com.example.mobilea1.mgp2dCore.other.AnimatedSprite;

import com.example.mobilea1.Combat.WeaponBase;
import com.example.mobilea1.GameManager;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class EnemyCharacter extends CharacterEntity {

    private final Bitmap sprite;
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

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.runhaolinkin2);
        sprite = Bitmap.createScaledBitmap(bmp, (int) size.x, (int) size.y, true);
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
