package com.example.mobilea1.Scenes;



import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.mobilea1.Camera;
import com.example.mobilea1.Physics.CollisionDetection;
import com.example.mobilea1.Entities.BackgroundEntity;
import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.EnemyCharacter;
import com.example.mobilea1.Entities.PlayerCharacter;
import com.example.mobilea1.Entities.Ground;
import com.example.mobilea1.UI.Button;
import com.example.mobilea1.UI.Joystick;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.GameScene;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _gameEntities = new Vector<>();
    Vector<CharacterEntity> _charEntities = new Vector<>();
    Vector<EnemyCharacter> _enemyEntities = new Vector<>();
    Vector<PlayerCharacter> _playerEntities = new Vector<>();

    float screenWidth;
    float screenHeight;

    Vector2 characterSize = new Vector2(100,100);
    Vector2 mapSize = new Vector2(5000,5000);
    Camera MainCam;
    Joystick joystick;
    Button jumpButton;
    Button switchButton;

    @Override
    public void onCreate()
    {
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        MainCam = new Camera();
        jumpButton = new Button(new Vector2(screenWidth - 200, screenHeight - 200),100, Button.TYPE.MomentaryPush);
        switchButton = new Button(new Vector2(screenWidth - 100, screenHeight - 400),100, Button.TYPE.Toggle);
        joystick = new Joystick(new Vector2(0,0), 70, 40);

        super.onCreate();
        _gameEntities.add(MainCam);
        _gameEntities.add(new BackgroundEntity(mapSize));
        _gameEntities.add(new Ground(new Vector2(mapSize.x * 0.9f,500)));
        _gameEntities.add(new PlayerCharacter(characterSize, 0, "MQQ"));
        //_gameEntities.add(new PlayerCharacter(characterSize, 1));
        //_gameEntities.add(new PlayerCharacter(characterSize, 2));
        _gameEntities.add(new EnemyCharacter(characterSize, 0, "Witz"));
        //_gameEntities.add(new EnemyCharacter(characterSize, 1));
        //_gameEntities.add(new EnemyCharacter(characterSize, 2));
        _gameEntities.add(joystick);
        _gameEntities.add(jumpButton);
        _gameEntities.add(switchButton);

        for(GameEntity entity: _gameEntities)
        {
            entity.show = true;
            entity.active = true;
            entity.ignoreRaycast = true;
        }

        if(getGround() != null)
        {
            getGround().setPosition(new Vector2(0, getGround().getSize().y * 0.5f));
            getGround().ignoreRaycast = false;
        }

        //use for loop to activate and show all
        for(GameEntity entity : _gameEntities)
        {
            if(entity instanceof CharacterEntity)
            {
                CharacterEntity C = (CharacterEntity) entity;
                C.alive = true;
                C.setPosition(new Vector2(0, 100));
                C.ignoreRaycast = false;
                C.onCreate();
                _charEntities.add(C);
                if(entity instanceof PlayerCharacter)
                {
                    PlayerCharacter P = (PlayerCharacter) entity;
                    _playerEntities.add(P);
                }
                else if(entity instanceof EnemyCharacter)
                {
                    EnemyCharacter E = (EnemyCharacter) entity;
                    _enemyEntities.add(E);
                }
            }
        }

    }
    private PlayerCharacter getPlayerEntity(int id)
    {
        for(GameEntity entity: _gameEntities) {
            if (entity instanceof PlayerCharacter) {
                PlayerCharacter PC = (PlayerCharacter) entity;
                if (PC.getID() == id) {
                    return PC;
                }
            }
        }
        return null;
    }
    private EnemyCharacter getEnemyEntity(int id)
    {
        for(GameEntity entity: _gameEntities) {
            if (entity instanceof EnemyCharacter) {
                EnemyCharacter EC = (EnemyCharacter) entity;
                if (EC.getID() == id) {
                    return EC;
                }
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

        float x = event.getX(index);   // pointer INDEX used
        float y = event.getY(index);


        switch(action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if(x < (screenWidth * 0.5))
                {
                    if (joystick.pointerID == -1 )
                    {
                        joystick.pointerID = pointerID;

                        joystick.setPosition(new Vector2(x, y));
                        joystick.setPressed(true);
                        joystick.resetActuator();
                        joystick.show = true;

                        System.out.println("joystick down " + joystick.pointerID);
                    }
                }
                else if (jumpButton.contains(x, y))
                {
                    if(jumpButton.pointerID == -1)
                    {
                        jumpButton.setPressed(true, pointerID);

                        System.out.println("jumpBtn down " + jumpButton.pointerID);
                    }
                }
                else if (switchButton.contains(x,y))
                {
                    if(switchButton.pointerID == -1)
                    {
                        switchButton.setPressed(true, pointerID);
                        switchButton.toggled = !switchButton.toggled;
                        System.out.println("switchBtn down " + switchButton.pointerID);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (joystick.isPressed())
                {
                    int stickIndex = event.findPointerIndex(joystick.pointerID);

                    if (stickIndex != -1) {
                        float moveX = event.getX(stickIndex);
                        float moveY = event.getY(stickIndex);
                        joystick.setActuator(new Vector2(moveX, moveY));
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                if (pointerID == joystick.pointerID)
                {
                   joystick.resetJoystick();
                    System.out.println("joystick up " + pointerID);
                }
                else if(pointerID == jumpButton.pointerID)
                {
                    jumpButton.Unpressed(pointerID);
                    System.out.println("jumpBtn up " + pointerID);
                }
                else if(pointerID == switchButton.pointerID)
                {
                    switchButton.setPressed(false, pointerID);

                    joystick.setPressed(false);
                    joystick.resetActuator();

                    System.out.println("switchBtn up " + pointerID);
                }
                break;
        }
        touchChecks(event);
    }

    public void touchChecks(MotionEvent event)
    {
        boolean leftSideTouched = false;
        boolean rightSideTouched = false;

        int i = 0;

        while (i < event.getPointerCount()) {
            float x = event.getX(i);

            if (x < screenWidth * 0.5f)
            {
                leftSideTouched = true;
            }
            if (x > screenWidth * 0.5f) {
                rightSideTouched = true;
            }
            i++;
        }

        if (!leftSideTouched)
        {
            // LEFT SIDE HAS NO INPUT
            joystick.resetJoystick();
        }
        if(!rightSideTouched)
        {
            jumpButton.reset();
        }
    }

    @Override
    public void onUpdate(float dt)
    {
        //get player
        PlayerCharacter chosenChar = getPlayerEntity(0);
        assert chosenChar != null;

        MainCam.setTarget(chosenChar);

        chosenChar.chosen = true;

        handleTouch();

        if(switchButton.toggled) //fire mode
        {
            float turnSpeed = 0.1f;
            float facingAngle = chosenChar.getAimAngle();

            chosenChar.stopMovement();
            chosenChar.setMovementDir(new Vector2(0,0));
            if (joystick.isPressed()) {
                float inputX = joystick.actuatorValues.x;

                chosenChar.setAimAngle(facingAngle - inputX * turnSpeed * dt);

                // Wrap angle
                if (facingAngle > Math.PI)
                {
                    chosenChar.setAimAngle(facingAngle - (float)(2 * Math.PI));
                }
                if (facingAngle < -Math.PI)
                {
                    chosenChar.setAimAngle(facingAngle + (float)(2 * Math.PI));
                }

                // Convert angle → direction
                chosenChar.setAimDir(new Vector2( (float)Math.cos(facingAngle), (float)Math.sin(facingAngle)));

            }
            if(jumpButton.isPressed(jumpButton.pointerID))
            {
                chosenChar.getWeapon(0).Shoot(_gameEntities);
            }
        }
        else //move mode
        {
            chosenChar.setMovementDir(new Vector2(joystick.actuatorValues.x, 0));

            if(jumpButton.isPressed(jumpButton.pointerID) && chosenChar.onGround)
            {
                chosenChar.Jump();
            }
        }

        for(int i = 0; i < _playerEntities.size(); i++)
        {
            PlayerCharacter pc = getPlayerEntity(i);
            assert pc != null;

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
        }
        for(int i = 0; i < _enemyEntities.size(); i++)
        {
            EnemyCharacter ec = getEnemyEntity(i);
            assert ec != null;

            Ground ground = getGround();
            assert ground != null;

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

        for(GameEntity entity: _gameEntities) {
            if (entity.canDestroy())
                continue;
            entity.onUpdate(dt);
        }
    }

    @Override
    public void onRender(Canvas canvas)
    {
        for(GameEntity entity: _gameEntities) {
            if (entity.canDestroy() || !entity.show)
                continue;

            entity.onRender(canvas);

        }
    }
}
