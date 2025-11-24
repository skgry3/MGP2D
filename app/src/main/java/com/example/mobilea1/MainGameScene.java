package com.example.mobilea1;

import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.GameScene;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _gameEntities = new Vector<>();
    MediaPlayer bgmPlayer;


    @Override
    public void onCreate()
    {
        super.onCreate();
        _gameEntities.add(new BackgroundEntity());
        _gameEntities.add(new PlayerCharacter());
        _gameEntities.add(new EnemyCharacter());

        PlayerCharacter Pcharacter = (PlayerCharacter) _gameEntities.get(1);
        Pcharacter.name = "Hur";

        EnemyCharacter Echaracter = (EnemyCharacter) _gameEntities.get(2);
        Echaracter.name = "Witz";
        Echaracter.moveSpeed = 20f;

        Pcharacter.onCreate();
        Echaracter.onCreate();

    }

    private  void handleTouch()
    {
        MotionEvent event = GameActivity.instance.getMotionEvent();
        if(event == null)
            return;

        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int pointerID = event.getPointerId(index);

        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //do something
                break;
            case MotionEvent.ACTION_MOVE:
                //do something
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

    }

    @Override
    public void onUpdate(float dt)
    {
        for(GameEntity entity: _gameEntities)
            entity.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        for(GameEntity entity: _gameEntities)
            entity.onRender(canvas);
    }
}
