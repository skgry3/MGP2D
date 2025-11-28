package com.example.mobilea1.mgp2dCore;

import android.graphics.Canvas;

import com.example.mobilea1.Camera;

public abstract class GameEntity {
    protected Vector2 size;
    public Vector2 getSize(){ return size;}
    public boolean show = false;
    public boolean active = false;
    protected Vector2 _position = new Vector2(0, 0);
    protected Vector2 _renderPosition = new Vector2(0,0);
    public Vector2 getPosition() { return _position.copy(); }
    public void setPosition(Vector2 position) { _position = position; }

    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void onUpdate(float dt)
    {
        if(show) {
            _renderPosition.x = _position.x - (getSize().x * 0.5f) - Camera.getOffset().x;
            _renderPosition.y = _position.y - (getSize().y * 0.5f) - Camera.getOffset().y;
        }
    }
    public abstract void onRender(Canvas canvas);
}
