package com.example.mobilea1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class CharacterEntity extends GameEntity implements IDamageable{
    public float health;
    public float moveSpeed;
    public float jumpHeight;
    public String name = "name";
    public boolean alive;

    public CharacterEntity()
    {
        health = 100f;
        moveSpeed = 10;
        jumpHeight = 5;
        alive = true;
        float sizeRelativeToScreen = 0.1f;
        _position = new Vector2(0,0);
    }

    @Override
    public void onUpdate(float dt) {
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
