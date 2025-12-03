package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Text extends GameEntity {
    protected final Paint _paint;

    Text(Vector2 pos, float textSize, int color, Paint.Align align, boolean isui) {
        _paint = new Paint();
        _paint.setColor(color);
        _paint.setTextSize(textSize);
        _paint.setTextAlign(align);
        _position = pos.copy();
        size = new Vector2(0,0);
        isUI = isui;

        Paint.FontMetrics fm = _paint.getFontMetrics();
        float textHeight = Math.abs(fm.top);
        _position = new Vector2( _position.x, _position.y + textHeight);
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
    }

}
