package com.example.mobilea1.Combat;

import android.graphics.Canvas;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.EnemyCharacter;
import com.example.mobilea1.Physics.Raycast;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public abstract class WeaponBase extends GameEntity {

    String name;

    float firerate;
    float reloadTime;
    int ammoAmt;
    int magAmt;
    int ammoPerMag;
    private float range;
    protected CharacterEntity owner;

    WeaponBase()
    {
        firerate = 0.1f;
        reloadTime = 0.1f;
        ammoAmt = 0;
        magAmt = 10;
        ammoPerMag = 10;
        range = 10f;
        show = true;
    }

    public abstract void Shoot(CharacterEntity shooter, Vector <CharacterEntity> targets);
    protected void ShootRaycast(CharacterEntity shooter, Vector<CharacterEntity> targets)
    {
        float p0_x = shooter.getPosition().x;
        float p0_y = shooter.getPosition().y;

        Vector2 dir = shooter.getAimDir().normalize();

        float rayLength = range;

        float p1_x = p0_x + dir.x * rayLength;
        float p1_y = p0_y + dir.y * rayLength;

        float closest = -1;

        for (CharacterEntity c : targets) {

            float left   = c.getPosition().x - c.getSize().x/2;
            float right  = c.getPosition().x + c.getSize().x/2;
            float top    = c.getPosition().y - c.getSize().y/2;
            float bottom = c.getPosition().y + c.getSize().y/2;

            float[] results = {
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, top, right, top),      // top
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, bottom, right, bottom),// bottom
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, top, left, bottom),    // left
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, right, top, right, bottom)   // right
            };

            for(float d : results){
                if(d != -1 && (closest == -1 || d < closest)){
                    closest = d;
                }
            }
        }
    }
    protected void ShootProjectile()
    {

    }

    @Override
    public void onUpdate(float dt) {
        Vector2 ownerPos = owner.getPosition();
        Vector2 offset = owner.getAimDir().normalize();

        _position = new Vector2(ownerPos.x + (-offset.x * size.x), ownerPos.y + (-offset.y * size.y));
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {

    }
}
