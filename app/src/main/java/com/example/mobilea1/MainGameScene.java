package com.example.mobilea1;

import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.GameScene;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _gameEntities = new Vector<>();
    MediaPlayer bgmPlayer;

    float screenWidth;

    enum gameEntities
    {
        Bg(0),
        Joystick(1),
        PC1(2),
        PC2(3),
        PC3(4),
        EC1(5),
        EC2(6),
        EC3(7);

        int entityID;
        gameEntities(int entityID)
        {
            this.entityID = entityID;
        }
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        _gameEntities.add(new BackgroundEntity());
        _gameEntities.add(new Joystick(new Vector2(0,0), 70, 40));
        _gameEntities.add(new PlayerCharacter(100));
        _gameEntities.add(new PlayerCharacter(100));
        _gameEntities.add(new PlayerCharacter(100));
        _gameEntities.add(new EnemyCharacter());
        _gameEntities.add(new EnemyCharacter());
        _gameEntities.add(new EnemyCharacter());

        getEntity(gameEntities.Bg.entityID).show = true;
        getEntity(gameEntities.PC1.entityID).show = true;

        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;

    }
    private GameEntity getEntity(int EntityID)
    {
        return _gameEntities.get(EntityID);
    }

    private  void handleTouch()
    {
        MotionEvent event = GameActivity.instance.getMotionEvent();
        if(event == null)
            return;

        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int pointerID = event.getPointerId(index);

        Joystick joystick = (Joystick) getEntity(gameEntities.Joystick.entityID);
        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //do something
                if(event.getRawX() < (screenWidth *0.5))
                {
                    joystick.setPosition(new Vector2(event.getRawX(),  event.getRawY()));
                    joystick.setPressed(true);
                    joystick.resetActuator();
                    joystick.show = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //do something
                if(joystick.isPressed())
                {
                    joystick.setActuator(new Vector2(event.getRawX(), event.getRawY()));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                joystick.show = false;
                joystick.resetActuator();
                joystick.setPressed(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

    }

    @Override
    public void onUpdate(float dt)
    {
        Joystick joystick = (Joystick) getEntity(gameEntities.Joystick.entityID);

        PlayerCharacter chosenChar = (PlayerCharacter) getEntity(gameEntities.PC1.entityID);
        chosenChar.chosen = true;

        handleTouch();
        chosenChar.setMovementDir(joystick.actuatorValues);

        for(GameEntity entity: _gameEntities)
            entity.onUpdate(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        for(GameEntity entity: _gameEntities)
        {
            if(entity.show)
            {
                entity.onRender(canvas);
            }
        }
    }

    private boolean isColliding(GameEntity entityA, GameEntity entityB)
    {
        float aLeft = entityA.getPosition().x - entityA.getSize().x / 2;
        float aRight = entityA.getPosition().x + entityA.getSize().x / 2;
        float aTop = entityA.getPosition().y - entityA.getSize().y / 2;
        float aBtm = entityA.getPosition().y + entityA.getSize().y / 2;

        float bLeft = entityB.getPosition().x - entityB.getSize().x / 2;
        float bRight = entityB.getPosition().x + entityB.getSize().x / 2;
        float bTop = entityB.getPosition().y - entityB.getSize().y / 2;
        float bBtm = entityB.getPosition().y + entityB.getSize().y / 2;

        // return result here...
    }

}
