package com.example.mobilea1.Entities;

import static com.example.mobilea1.Scenes.MainGameScene.mapSize;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.mobilea1.Combat.IDamageable;
import com.example.mobilea1.Combat.WeaponBase;
import com.example.mobilea1.Combat.Weapons.SMG;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class CharacterEntity extends PhysicObj implements IDamageable {
    public float health;
    public float moveSpeed;
    public float maxSpeed;
    public float jumpHeight;
    public boolean onGround;
    public String name = "name";
    public boolean alive;
    public boolean chosen;
    protected int ID;
    public boolean isPlayer;
    public boolean isEnemy;
    private Vector2 moveDir = new Vector2(0,0);
    private Vector2 aimDir = new Vector2(0,0);
    Vector<WeaponBase> _weapons = new Vector<>();
    protected boolean showWeapons = true;

    Paint debugPaint = new Paint();
    {
        debugPaint.setColor(Color.RED);
        debugPaint.setStrokeWidth(5);
    }
    public CharacterEntity()
    {
        health = 100f;
        moveSpeed = 10;
        maxSpeed = 200;
        jumpHeight = 1000;
        alive = true;
        _position = new Vector2(0,0);
        isPlayer = false;
        isEnemy = false;
        chosen = false;
    }
    public void onCreate()
    {
        _weapons.add(new SMG(this));
    }

    public int getID() {
        return ID;
    }
    public void setMovementDir(Vector2 dir)
    {
        moveDir = dir;
    }
    public void Jump()
    {
        AddForce(jumpHeight, new Vector2(0,-1), ForceMode.Impulse);
    }
    private float aimAngle = 0f;
    public void setAimAngle(float angle)
    {
        aimAngle = angle;
    }
    public float getAimAngle()
    {
        return aimAngle;
    }
    public void setAimDir(Vector2 dir)
    {
        if(!dir.equals(new Vector2(0, 0)))
        {
            dir.normalize();
        }
        aimDir = dir;
    }
    public Vector2 getAimDir()
    {
        return aimDir.copy();
    }
    public WeaponBase getWeapon(int id)
    {
        return _weapons.get(id);
    }
    @Override
    public void onUpdate(float dt) {
        AddForce(moveSpeed, moveDir, ForceMode.Force);

        if(VerticalVel > 1 && onGround)
            VerticalVel = 1;

        HorizontalVel = Math.clamp(HorizontalVel,-maxSpeed, maxSpeed );

        super.onUpdate(dt);

        for(WeaponBase weapon : _weapons)
        {
            weapon.onUpdate(dt);
        }

        if(_position.y > mapSize.y * 0.5)
        {
            die();
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        for(WeaponBase weapon : _weapons)
        {
            if(showWeapons)
                weapon.onRender(canvas);
        }
    }

    @Override
    public void takeDmg(float Dmg) {
        health -= Dmg;
        if(health <= 0)
        {
            die();
        }
    }

    @Override
    public void die() {
        //delete character
        alive = false;
        active = false;
        ignoreRaycast = true;
        destroy();
        System.out.println( name + " died");
    }

    public void onTurnStart() {
        for(WeaponBase weapon : _weapons)
        {
            if(weapon.shot)
            {
                weapon.shot = false;
            }
        }
    }
}
