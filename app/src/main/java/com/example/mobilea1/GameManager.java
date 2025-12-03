package com.example.mobilea1;


import android.graphics.Canvas;

import com.example.mobilea1.Combat.TurnBaseSystem;
import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.EnemyCharacter;
import com.example.mobilea1.Entities.Ground;
import com.example.mobilea1.Entities.PlayerCharacter;
import com.example.mobilea1.Inputs.InputManager;
import com.example.mobilea1.Physics.CollisionDetection;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class GameManager {
    TurnBaseSystem TBS;
    InputManager im;
    Vector2 characterSize = new Vector2(100,100);
    Vector<GameEntity> _gameEntities = new Vector<>();
    Vector<CharacterEntity> _charEntities = new Vector<>();
    private boolean isLoaded;
    private GameManager()
    {
        LoadGame();
    }
    private static class BillPughSingleton {
        private static final GameManager INSTANCE = new GameManager();
    }
    public static GameManager getInstance()
    {
        return BillPughSingleton.INSTANCE;
    }
    private float waitTime;
    private void LoadGame()
    {
        waitTime = 3f;

        TBS = TurnBaseSystem.getInstance();
        im = InputManager.getInstance();

        Ground ground = new Ground(new Vector2(2000,1000));
        _gameEntities.add(ground);

        float groundTopY = ground.getPosition().y - ground.getSize().y * 0.5f;
        float groundLeftX = ground.getPosition().x - ground.getSize().x * 0.5f;
        float groundRightX = ground.getPosition().x + ground.getSize().x * 0.5f;

        for (int i = 0; i < 3; i++) {
            float ranPX = randomXOnGround(groundLeftX, groundRightX, characterSize.x);
            float spawnY = groundTopY - characterSize.y * 2;
            _gameEntities.add(new PlayerCharacter(characterSize, i, "MQQ" + (i+1), new Vector2(ranPX, spawnY), this));
        }

        for (int i = 0; i < 3; i++) {
            float ranEX = randomXOnGround(groundLeftX, groundRightX, characterSize.x);
            float spawnY = groundTopY - characterSize.y * 2;
            _gameEntities.add(new EnemyCharacter(characterSize, i, "Witz" + (i+1), new Vector2(ranEX, spawnY), this));
        }

        for(GameEntity e: _gameEntities)
        {
            e.show = true;
            e.active = true;
            e.ignoreRaycast = false;
            e.isUI = false;

            if(e instanceof CharacterEntity)
            {
                _charEntities.add((CharacterEntity) e);
                ((CharacterEntity) e).onCreate();
            }
        }

        assert TBS != null;
        TBS.initialise(_charEntities);

        isLoaded = true;
    }
    private float randomXOnGround(float groundLeft, float groundRight, float charWidth) {
        float minX = groundLeft + charWidth * 2;
        float maxX = groundRight - charWidth * 2;
        return minX + (float)Math.random() * (maxX - minX);
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }
    public TurnBaseSystem getTBSInstance()
    {
        return TBS;
    }
    public Vector<GameEntity> getGameEntities()
    {
        return _gameEntities;
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
        for(GameEntity entity: _gameEntities) {
            if (entity instanceof Ground) {
                return (Ground) entity;
            }
        }
        return null;
    }
    public void gameUpdate(float dt) {

        if(waitTime > 0) {
            waitTime -= dt;
            return;
        }

        if(TBS.getState() == TurnBaseSystem.GameState.RESOLVING) {
           TBS.actionResult(dt);
        }
        else {
            //get which char turn
            CharacterEntity current = TBS.getCurrentEntity();
            CameraManager.getInstance().getMainCam().setTarget(current);
            //no more char alive
            if (current == null)
                return;

            //died during turn
            if (!current.alive) {
                TBS.actionCompleted(true);
            }

            //handle player logic
            if (current.isPlayer) {
                PlayerCharacter chosenPlayer = (PlayerCharacter) current;
                chosenPlayer.actions(dt);
            }

            //handle enemy logic
            else if (current.isEnemy) {
                EnemyCharacter chosenEnemy = (EnemyCharacter) current;
                chosenEnemy.runAI(dt);
            }
        }

        for(GameEntity e: _gameEntities)
        {
            if (e.canDestroy())
                continue;
            e.onUpdate(dt);
        }
        handleCollisions();
    }
    public void handleCollisions()
    {
        //collisions with ground
        for(int i = 0; i < _charEntities.size(); i++)
        {
            CharacterEntity ce = _charEntities.get(i);

            Ground ground = getGround();
            assert ground != null;

            if(CollisionDetection.OverlapCircleToAABB(ce, ground))
            {
                // Calculate the overlap distances to move the objects apart
                float overlapX = (ce.getSize().x * 0.5f) + (ground.getSize().x * 0.5f) - Math.abs(ground.getPosition().x - ce.getPosition().x);
                float overlapY = (ce.getSize().y * 0.5f) + (ground.getSize().y * 0.5f) - Math.abs(ground.getPosition().y - ce.getPosition().y);

                // Find the smallest move distance
                float resolveX = 0;
                float resolveY = 0;
                if (overlapX < overlapY)
                {
                    if (ce.getPosition().x < ground.getPosition().x)
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
                    if (ce.getPosition().y < ground.getPosition().y)
                    {
                        resolveY = -overlapY;
                    }
                    else
                    {
                        resolveY = overlapY;
                    }
                }

                ce.setPosition(new Vector2(ce.getPosition().x + resolveX, ce.getPosition().y + resolveY));

                ce.onGround = true;
            }
            else
            {
                ce.onGround = false;
            }
        }
    }
    public void render(Canvas canvas) {
        for(GameEntity e: _gameEntities)
        {
            if(e.canDestroy())
                continue;

            e.onRender(canvas);
        }
    }
}
