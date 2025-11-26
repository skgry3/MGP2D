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
    float screenHeight;

    Vector2 characterSize = new Vector2(100,100);
    Vector2 mapSize = new Vector2(5000,5000);

    Button jumpButton;

    @Override
    public void onCreate()
    {
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        jumpButton = new Button(new Vector2(screenWidth - 200, screenHeight - 200),100);

        super.onCreate();
        _gameEntities.add(new Camera());
        _gameEntities.add(new BackgroundEntity(mapSize));
        _gameEntities.add(new Ground(new Vector2(mapSize.x * 0.9f,500)));
        _gameEntities.add(new PlayerCharacter(characterSize, 0));
        _gameEntities.add(new PlayerCharacter(characterSize, 1));
        _gameEntities.add(new PlayerCharacter(characterSize, 2));
        _gameEntities.add(new EnemyCharacter(characterSize, 0));
        _gameEntities.add(new EnemyCharacter(characterSize, 1));
        _gameEntities.add(new EnemyCharacter(characterSize, 2));
        _gameEntities.add(new Joystick(new Vector2(0,0), 70, 40));
        _gameEntities.add(jumpButton);

        for(GameEntity entity: _gameEntities)
        {
            entity.show = true;
            entity.active = true;
        }


        if(getGround() != null)
        {
            getGround().setPosition(new Vector2(0, getGround().getSize().y * 0.5f));
        }

        //use for loop to activate and show all
        for(GameEntity entity : _gameEntities)
        {
            if(entity instanceof CharacterEntity)
            {
                CharacterEntity C = (CharacterEntity) entity;
                C.alive = true;
                C.setPosition(new Vector2(0, 100));
            }
        }

    }
    private CharacterEntity getCharacterEntity(int CharEntityID, boolean player)
    {
        CharacterEntity result = null;
        for(GameEntity entity: _gameEntities)
        {
            if(player)
            {
                if (entity instanceof PlayerCharacter) {
                    CharacterEntity PC = (CharacterEntity) entity;
                    if (PC.getID() == CharEntityID) {
                        result = PC;
                        break;
                    }
                }
            }
            else if (entity instanceof EnemyCharacter) {
                CharacterEntity EC = (CharacterEntity) entity;
                if (EC.getID() == CharEntityID) {
                    result = EC;
                    break;
                }
            }
        }
        return result;
    }
    private Camera getCam()
    {
        for(GameEntity entity: _gameEntities)
        {
            if(entity instanceof Camera)
            {
                return (Camera) entity;
            }
        }
        return null;
    }
    private BackgroundEntity getBg()
    {
        for(GameEntity entity: _gameEntities)
        {
            if(entity instanceof BackgroundEntity)
            {
                return (BackgroundEntity) entity;
            }
        }
        return null;
    }
    private Joystick getJoystick()
    {
        for(GameEntity entity: _gameEntities)
        {
            if(entity instanceof Joystick)
            {
                return (Joystick) entity;
            }
        }
        return null;
    }
    private Ground getGround()
    {
        for(GameEntity entity: _gameEntities)
        {
            if(entity instanceof Ground)
            {
                return (Ground) entity;
            }
        }
        return null;
    }
    private  void handleTouch()
    {
        MotionEvent event = GameActivity.instance.getMotionEvent();
        if(event == null)
            return;

        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int pointerID = event.getPointerId(index);

        Joystick joystick = getJoystick();
        assert joystick != null;

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

                if(jumpButton.contains(event.getRawX(), event.getRawY()))
                {
                    jumpButton.setPressed(true);
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

                jumpButton.setPressed(false);

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

    }

    @Override
    public void onUpdate(float dt)
    {
        Camera cam = getCam();
        assert cam != null;

        Joystick joystick = getJoystick();
        assert joystick != null;

        PlayerCharacter chosenChar = (PlayerCharacter) getCharacterEntity(1, true);
        assert chosenChar != null;

        cam.setTarget(chosenChar);

        chosenChar.chosen = true;

        handleTouch();

        chosenChar.setMovementDir(joystick.actuatorValues);

        if(jumpButton.isPressed() && chosenChar.onGround)
        {
            chosenChar.Jump();
        }

        for(GameEntity entity: _gameEntities)
            entity.onUpdate(dt);

        for(int i = 0; i < 3; i++)
        {
            PlayerCharacter pc = (PlayerCharacter) getCharacterEntity(i, true);
            assert pc != null;
            EnemyCharacter ec = (EnemyCharacter) getCharacterEntity(i, false);
            assert ec != null;

            Ground ground = getGround();
            assert ground != null;

            if(CollisionDetection.OverlapCircleToAABB(pc, ground))
            {
                // Calculate the overlap distances to move the objects apart
                float overlapX = (pc.getSize().x * 0.5f) + (ground.getSize().x * 0.5f) - Math.abs(ground.getPosition().x - pc.getPosition().x);
                float overlapY = (pc.getSize().y * 0.5f) + (ground.getSize().y * 0.5f) - Math.abs(ground.getPosition().y - pc.getPosition().y);

                // Find the smallest move distance
                float resolveX = 0;
                float resolveY = 0;
                if (overlapX < overlapY)
                {
                    if (pc.getPosition().x < ground.getPosition().x)
                    {
                        resolveX = -overlapX;
                    }
                    else
                    {
                        resolveX = overlapX;
                    }
                }
                else
                {
                    if (pc.getPosition().y < ground.getPosition().y)
                    {
                        resolveY = -overlapY;
                    }
                    else
                    {
                        resolveY = overlapY;
                    }
                }

                pc.setPosition(new Vector2(pc.getPosition().x + resolveX, pc.getPosition().y + resolveY));

                pc.onGround = true;
            }
            else
            {
                pc.onGround = false;
            }

            if(CollisionDetection.OverlapCircleToAABB(ec, ground))
            {
                // Calculate the overlap distances to move the objects apart
                float overlapX = (ec.getSize().x * 0.5f) + (ground.getSize().x * 0.5f) - Math.abs(ground.getPosition().x - ec.getPosition().x);
                float overlapY = (ec.getSize().y * 0.5f) + (ground.getSize().y * 0.5f) - Math.abs(ground.getPosition().y - ec.getPosition().y);

                // Find the smallest move distance
                float resolveX = 0;
                float resolveY = 0;
                if (overlapX < overlapY)
                {
                    if (ec.getPosition().x < ground.getPosition().x)
                    {
                        resolveX = -overlapX;
                    }
                    else
                    {
                        resolveX = overlapX;
                    }
                }
                else
                {
                    if (ec.getPosition().y < ground.getPosition().y)
                    {
                        resolveY = -overlapY;
                    }
                    else
                    {
                        resolveY = overlapY;
                    }
                }
                ec.setPosition(new Vector2(ec.getPosition().x + resolveX, ec.getPosition().y + resolveY));

                ec.onGround = true;
            }
            else
            {
                ec.onGround = false;
            }
        }
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
}
