package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.mgp2dCore.Vector2;

public class DmgNum extends Text{
    int num;
    float lifetime = 2f;
    float lifetimer;
    float ranXDiff;
    public DmgNum(int Num, Vector2 pos, float textSize, int color, Paint.Align align, boolean isui) {
        super(pos, textSize, color, align, isui);

        ranXDiff = textSize * 4;

        num = Num;
        lifetimer = lifetime;
        show = true;
        active = true;

        _position.x += (float) ((Math.random() * ranXDiff) - (ranXDiff * 0.5f));
    }

    @Override
    public void onUpdate(float dt) {
        if(lifetimer < 0)
        {
            show = false;
            destroy();
        }
        lifetimer -= dt;

        _position.y -= 10 * dt;
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText(String.valueOf(num), _renderPosition.x, _renderPosition.y, _paint);
    }
}
