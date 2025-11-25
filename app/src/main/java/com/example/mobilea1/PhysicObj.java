package com.example.mobilea1;

import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.GameEntity;

public class PhysicObj extends GameEntity {
    int gravity = 10;

    @Override
    public void onUpdate(float dt) {
        _position.y += gravity * dt;
    }

    @Override
    public void onRender(Canvas canvas) {

    }
}
