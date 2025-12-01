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
    public Vector2 getRenderPosition() { return _renderPosition.copy(); }
    protected float _rotationDeg = 0f;
    protected float _lastRotation = Float.NaN;
    public void setRotation(float rotateDeg) { _rotationDeg = rotateDeg; }
    public float getRotation() { return _rotationDeg; }
    public Vector2 rotateTowards(Vector2 current, Vector2 target, float maxRadiansDelta) {
        // Get the angle between the two vectors
        float angleCurrent = (float) Math.atan2(current.y, current.x);
        float angleTarget = (float) Math.atan2(target.y, target.x);

        // Compute shortest angle difference
        float delta = angleTarget - angleCurrent;
        delta = (float) Math.atan2(Math.sin(delta), Math.cos(delta)); // normalize to [-π, π]

        // Clamp rotation speed
        float rotation = Math.max(-maxRadiansDelta, Math.min(maxRadiansDelta, delta));

        // Apply rotation
        float newAngle = angleCurrent + rotation;

        return new Vector2((float)Math.cos(newAngle), (float)Math.sin(newAngle));
    }
    protected Vector2 facingDir = new Vector2(0,0);
    public Vector2 getFacingDir() { return facingDir.copy(); }
    public void setFacingDir(Vector2 dir) { facingDir = dir;}
    protected int flip = 1;
    protected int lastFlip = 1;
    public int getFlip() { return flip; }
    public void setFlip(int Flip) { flip = Flip; }
    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }
    public boolean ignoreRaycast = false;

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void onUpdate(float dt)
    {
        if(facingDir.x < 0)
        {
            flip = -1;
        }
        else {
            flip = 1;
        }
        if(show) {
            _renderPosition.x = _position.x - (getSize().x * 0.5f) - Camera.getOffset().x;
            _renderPosition.y = _position.y - (getSize().y * 0.5f) - Camera.getOffset().y;
        }
    }
    public abstract void onRender(Canvas canvas);
}
