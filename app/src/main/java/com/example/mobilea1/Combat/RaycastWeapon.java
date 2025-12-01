package com.example.mobilea1.Combat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.mobilea1.Camera;
import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.Ground;
import com.example.mobilea1.Physics.Raycast;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class RaycastWeapon extends WeaponBase{
    protected Raycast.RaycastHit hit = null;
    float p1_xRP = 0;
    float p1_yRP = 0;
    float pointxRP = 0;
    float pointyRP = 0;

    @Override
    public void Shoot(Vector<GameEntity> targets) {
        for(int i = 0; i < sprayAmt; i++) {
            hit = ShootRaycast(owner, targets);
            if (hit == null) {
                System.out.println("hit nothing");
                return;
            }

            if (hit.target instanceof CharacterEntity) {
                CharacterEntity hitTarget = (CharacterEntity) hit.target;
                hitTarget.takeDmg(dmg);
                System.out.println("hit " + hitTarget.name + " for " + dmg);
            }
            if(hit.target instanceof Ground)
            {
                System.out.println("hit ground for " + dmg);
            }
        }
    }

    @Override
    public void onUpdate(float dt) {

        float p0_x = _position.x - size.x * 0.5f;
        float p0_y = _position.y - size.y * 0.5f;

        Vector2 dir = owner.getAimDir().normalize();

        float rayLength = range;

        p1_x = p0_x + dir.x * rayLength;
        p1_y = p0_y + dir.y * rayLength;

        p1_xRP = p1_x - Camera.getOffset().x;
        p1_yRP = p1_y - Camera.getOffset().y;
        if(hit != null)
        {
            pointxRP = hit.point.x - Camera.getOffset().x;
            pointyRP = hit.point.y - Camera.getOffset().y;
        }
        super.onUpdate(dt);

    }
    Paint debugPaint = new Paint();
    {
        debugPaint.setColor(Color.RED);
        debugPaint.setStrokeWidth(5);
    }
    Paint hitPaint = new Paint();
    {
        hitPaint.setColor(Color.GREEN);
        hitPaint.setStrokeWidth(5);
    }
    @Override
    public void onRender(Canvas canvas) {
        if(hit != null)
            canvas.drawLine(_renderPosition.x,_renderPosition.y,pointxRP, pointyRP, hitPaint);

        canvas.drawLine(_renderPosition.x,_renderPosition.y, p1_xRP, p1_yRP, debugPaint);

    }
}
