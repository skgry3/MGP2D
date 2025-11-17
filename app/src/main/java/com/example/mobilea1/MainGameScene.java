package com.example.mobilea1;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.GameScene;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _gameEntities = new Vector<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        _gameEntities.add(new BackgroundEntity());

        PlayerCharacter playerCharacter0 = new PlayerCharacter();
        playerCharacter0.name = "Witz";

        playerCharacter0.onCreate();
    }


    @Override
    public void onUpdate(float dt)
    {
        for(GameEntity entity: _gameEntities)
            entity.onUpdate(dt);

        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if(motionEvent == null)
            return;
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            GameActivity.instance.finish();
    }

    @Override
    public void onRender(Canvas canvas)
    {
        for(GameEntity entity: _gameEntities)
            entity.onRender(canvas);
    }
}
