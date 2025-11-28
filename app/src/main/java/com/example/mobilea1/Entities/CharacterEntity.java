package com.example.mobilea1.Entities;

import static com.example.mobilea1.Physics.Raycast.getRayCast;
import static com.example.mobilea1.Physics.Raycast.raycastHitPoint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.mobilea1.Combat.IDamageable;
import com.example.mobilea1.Combat.RaycastWeapon;
import com.example.mobilea1.Combat.WeaponBase;
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
    protected int ID;

    private Vector2 moveDir = new Vector2(0,0);
    private Vector2 aimDir = new Vector2(0,0);
    Vector<WeaponBase> _weapons = new Vector<>();
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
    }
    public void onCreate()
    {
        _weapons.add(new RaycastWeapon(this, "SMG", new Vector2(50,50)));
    }

    public int getID() {
        return ID;
    }
    public void setMovementDir(Vector2 dir)
    {
        moveDir = dir;
        moveDir.y = 0;
    }
    public void Jump()
    {
        AddForce(jumpHeight, new Vector2(0,-1), ForceMode.Impulse);
    }
    public void setAimDir(Vector2 dir)
    {
        aimDir = dir;
    }
    public Vector2 getAimDir()
    {
        return aimDir;
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
    }

    @Override
    public void onRender(Canvas canvas) {
        for(WeaponBase weapon : _weapons)
        {
            weapon.onRender(canvas);
        }
    }

    @Override
    public void takeDmg(int Dmg) {
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
        System.out.println("character died");
    }
}
