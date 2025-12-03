package com.example.mobilea1.Combat.Weapons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.mobilea1.Combat.RaycastWeapon;
import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.R;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class SMG extends RaycastWeapon {
    private final Bitmap sprite;
    public SMG(CharacterEntity Owner) {
        owner = Owner;
        name = "SMG";
        size = new Vector2(50,50);
        dmg = 10f;
        range = 500f;
        sprayAmt = 5;
        sprayDeg = 5f;
        shot = false;

        Bitmap spriteBmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.vectorsmg);
        sprite = Bitmap.createScaledBitmap(spriteBmp, (int) size.x, (int) size.y, true);
    }

    @Override
    public void Shoot(Vector<GameEntity> targets)
    {
        if(!shot) {
            shot = true;
            super.Shoot(targets);
        }
    }

    @Override
    public void onUpdate(float dt) {

        super.onUpdate(dt);

    }


    @Override
    public void onRender(Canvas canvas) {

        canvas.save();
        canvas.rotate(_rotationDeg, _renderPosition.x , _renderPosition.y);
        canvas.drawBitmap(sprite, _renderPosition.x, _renderPosition.y, null);
        canvas.restore();
        super.onRender(canvas);
    }
}
