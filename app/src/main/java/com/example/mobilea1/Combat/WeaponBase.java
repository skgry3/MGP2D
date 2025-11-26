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

    public abstract void Shoot();
    protected void ShootRaycast(CharacterEntity player, Vector<EnemyCharacter> enemies, WeaponBase weaponBase)
    {
        float p0_x = player.getPosition().x;
        float p0_y = player.getPosition().y;

        Vector2 dir = player.getAimDir().normalize();

        float rayLength = weaponBase.range;

        float p1_x = p0_x + dir.x * rayLength;
        float p1_y = p0_y + dir.y * rayLength;

        float closest = -1;

        for (EnemyCharacter e : enemies) {

            float left   = e.getPosition().x - e.getSize().x/2;
            float right  = e.getPosition().x + e.getSize().x/2;
            float top    = e.getPosition().y - e.getSize().y/2;
            float bottom = e.getPosition().y + e.getSize().y/2;

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
    public void onRender(Canvas canvas)
    {

    }
}
