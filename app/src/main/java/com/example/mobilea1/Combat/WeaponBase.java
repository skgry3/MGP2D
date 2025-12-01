package com.example.mobilea1.Combat;

import android.graphics.Canvas;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Physics.Raycast;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public abstract class WeaponBase extends GameEntity {

    protected String name;
    protected float dmg;
    public float range;
    protected int sprayAmt;
    protected float sprayDeg;
     float  p1_x;
    float p1_y;
    protected CharacterEntity owner;
    public boolean shot;
    WeaponBase()
    {
        dmg = 100;
        range = 500f;
        sprayAmt = 0;
        sprayDeg = 0;
        show = true;
    }

    public abstract void Shoot(Vector<GameEntity> targets);
    protected Raycast.RaycastHit ShootRaycast(CharacterEntity shooter, Vector<GameEntity> targets) {
        float p0_x = _position.x;
        float p0_y = _position.y;

        Vector2 dir = shooter.getAimDir().normalize();
        float rayLength = range;

        float half = sprayDeg * 0.5f;
        float angleOffset = (float)((Math.random() * sprayDeg) - half); // e.g. -5° to +5°

        float aimAngle = (float)Math.atan2(dir.y, dir.x);
        float sprayedAngle = aimAngle + (float)Math.toRadians(angleOffset);

        dir = new Vector2(
                (float)Math.cos(sprayedAngle),
                (float)Math.sin(sprayedAngle)
        );

         p1_x = p0_x + dir.x* rayLength;
         p1_y = p0_y + dir.y * rayLength;

        float closestDist = Float.MAX_VALUE;
        GameEntity closestTarget = null;

        for (GameEntity c : targets) {

            if(c.ignoreRaycast)
                continue;
            if(c == shooter)
                continue;

            float left = c.getPosition().x - c.getSize().x / 2;
            float right = c.getPosition().x + c.getSize().x / 2;
            float top = c.getPosition().y - c.getSize().y / 2;
            float bottom = c.getPosition().y + c.getSize().y / 2;

            float[] results = {
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, top, right, top),      // top
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, bottom, right, bottom),// bottom
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, left, top, left, bottom),    // left
                    Raycast.getRayCast(p0_x, p0_y, p1_x, p1_y, right, top, right, bottom)   // right
            };

            for (float d : results) {
                if (d != -1 && d < closestDist) {
                    closestDist = d;
                    closestTarget = c;
                }
            }
        }
        if (closestTarget == null) return null;

        Vector2 hitPoint = new Vector2(
                p0_x + dir.x * closestDist,
                p0_y + dir.y * closestDist
        );

        return new Raycast.RaycastHit(closestTarget, hitPoint);
    }

    protected void ShootProjectile()
    {

    }

    @Override
    public void onUpdate(float dt) {
        Vector2 ownerPos = owner.getPosition();
        facingDir = owner.getAimDir();
        _rotationDeg = (float) Math.toDegrees(owner.getAimAngle());
        _position = new Vector2( ownerPos.x + facingDir.x , ownerPos.y + facingDir.y );
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
    }
}
