package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class NameText extends Text{
    String Name;
    CharacterEntity entity;

    public NameText(CharacterEntity Entity, float textSize, int color, Paint.Align align, boolean isui) {
        super(Entity.getPosition(), textSize, color, align, isui);

        entity = Entity;
        Name = entity.name;
        show = true;
        active = true;
    }

    @Override
    public void onUpdate(float dt) {

        _position = new Vector2( entity.getPosition().x, entity.getPosition().y - 100f);

        if(!entity.alive)
        {
            destroy();
        }
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText(entity.name, _renderPosition.x, _renderPosition.y, _paint);
    }
}
