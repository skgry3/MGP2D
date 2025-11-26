package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class CharacterEntity extends PhysicObj implements IDamageable{
    public float health;
    public float moveSpeed;
    public float maxSpeed;
    public float jumpHeight;
    public boolean onGround;
    public String name = "name";
    public boolean alive;
    protected int ID;

    public CharacterEntity()
    {
        health = 100f;
        moveSpeed = 10;
        maxSpeed = 200;
        jumpHeight = 1000;
        alive = true;
        _position = new Vector2(0,0);
    }

    public int getID() {
        return ID;
    }

    @Override
    public void onUpdate(float dt) {

        if(VerticalVel > 1 && onGround)
            VerticalVel = 1;

        HorizontalVel = Math.clamp(HorizontalVel,-maxSpeed, maxSpeed );

        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {

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
