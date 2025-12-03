package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import org.w3c.dom.Entity;

public class HealthText extends Text{
    int num;
    CharacterEntity entity;

    public HealthText( CharacterEntity Entity, float textSize, int color, Paint.Align align, boolean isui) {
        super(Entity.getPosition(), textSize, color, align, isui);

        entity = Entity;
        num = (int) entity.health;
        show = true;
        active = true;

        System.out.println("Health text create");
    }

    @Override
    public void onUpdate(float dt) {
        num = (int) entity.health;
        _position = new Vector2( entity.getPosition().x, entity.getPosition().y - 100f);

        if(num <= 0)
        {
            destroy();
            System.out.println("Health text destroying");
        }
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText(String.valueOf(num), _renderPosition.x, _renderPosition.y, _paint);
    }
}
