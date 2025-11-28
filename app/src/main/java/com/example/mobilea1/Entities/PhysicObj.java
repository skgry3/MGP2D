package com.example.mobilea1.Entities;

import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class PhysicObj extends GameEntity {
    float gravity = 75;
    float friction = 0.1f;

    float HorizontalVel;
    float VerticalVel;
    public enum ForceMode
    {
        Impulse,
        Force,
    }
    public void AddForce(float magnitude, Vector2 direction, ForceMode mode)
    {
        switch(mode)
        {
            case Impulse:
                if(direction.x != 0)
                {
                    HorizontalVel = magnitude * direction.x;
                }
                if(direction.y != 0)
                {
                    VerticalVel = magnitude * direction.y;
                }
                break;

            case Force:
                if(direction.x != 0)
                {
                    HorizontalVel -= magnitude * direction.x;
                }
                if(direction.y != 0)
                {
                    VerticalVel += magnitude * direction.y;
                }
                break;
        }
    }
    public void stopMovement()
    {
        VerticalVel = 0;
        HorizontalVel = 0;
    }
    private void externalForces()
    {
        VerticalVel += gravity;
        HorizontalVel += (friction * -HorizontalVel * 0.5f);
    }
    @Override
    public void onUpdate(float dt) {
        if(!active)
            return;

        externalForces();

        if(Math.abs(HorizontalVel) < 5)
            HorizontalVel = 0;

        if(VerticalVel > gravity * 1.5)
            VerticalVel = gravity * 1.5f;

        _position.y +=  VerticalVel * dt ;
        _position.x +=  HorizontalVel * dt;

        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {

    }
}
