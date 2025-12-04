package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.mgp2dCore.Vector2;

public class TextBox extends Text{
String text;
    public TextBox(String Text, Vector2 pos, float textSize, int color, Paint.Align align, boolean isui) {
        super(pos, textSize, color, align, isui);
        text = Text;
    }
    public void changeText(String Text)
    {
        text = Text;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {

        canvas.drawText(text, _position.x, _position.y, _paint);
    }
}
