package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.mgp2dCore.Vector2;

public class FPSText extends Text{
    private int _frameCount;
    private long _lastTime;
    private float _fps;
    FPSText(Vector2 pos, float textSize, int color, Paint.Align align, boolean isui) {
        super(pos, textSize, color, align, isui);

    }

    @Override
    public void onUpdate(float dt) {

        _frameCount++;
        long currentTime = System.currentTimeMillis();
        if(currentTime - _lastTime > 1000) {
            _fps = (_frameCount * 1000.f) / (currentTime - _lastTime);
            _lastTime = currentTime;
            _frameCount = 0;
        }
        super.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText("FPS: " + (int)_fps, _position.x, _position.y, _paint);
    }
}
