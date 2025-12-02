package com.example.mobilea1.Outputs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class Text extends GameEntity {
    private final Paint _paint;
    private int _screenWidth;
    private int _screenHeight;
    private int _frameCount;
    private long _lastTime;
    private float _fps;

    Text(int color) {
        _paint = new Paint();
        _paint.setColor(color);
        _paint.setTextSize(75);
        _paint.setTextAlign(Paint.Align.RIGHT);
        _screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        _screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        _position = new Vector2(_screenWidth * 0.5f, 75);
        size = new Vector2(0,0);
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        _frameCount++;
        long currentTime = System.currentTimeMillis();
        if(currentTime - _lastTime > 1000) {
            _fps = (_frameCount * 1000.f) / (currentTime - _lastTime);
            _lastTime = currentTime;
            _frameCount = 0;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText("FPS: " + (int)_fps, _position.x, _position.y, _paint);
    }

}
