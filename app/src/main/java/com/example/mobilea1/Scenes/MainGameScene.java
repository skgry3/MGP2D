package com.example.mobilea1.Scenes;



import android.graphics.Canvas;

import com.example.mobilea1.CameraManager;
import com.example.mobilea1.Entities.BackgroundEntity;
import com.example.mobilea1.GameManager;
import com.example.mobilea1.Inputs.InputManager;

import com.example.mobilea1.Outputs.TextManager;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.GameScene;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _cameraEntities = new Vector<>();
    Vector<BackgroundEntity> _bgEntities = new Vector<>();
    float screenWidth;
    float screenHeight;
    Vector2 mapSize = new Vector2(5000,5000);

    CameraManager cm;
    GameManager gm;
    InputManager im;
    TextManager tm;

    @Override
    public void onCreate()
    {
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        super.onCreate();
        _bgEntities.add(new BackgroundEntity(mapSize));

        for(BackgroundEntity entity: _bgEntities)
        {
            entity.show = true;
            entity.active = true;
            entity.ignoreRaycast = true;
            entity.isUI = false;
        }

        cm = CameraManager.getInstance();
        tm = TextManager.getInstance();
        gm = GameManager.getInstance();
        im = InputManager.getInstance();

    }

    @Override
    public void onUpdate(float dt)
    {
        im.update(dt);
        if(!gm.isLoaded()) return;

        cm.update(dt);
        //handle touch
        InputManager.getInstance().handleTouch();

        gm.gameUpdate(dt);

        //entity updates
        for(GameEntity entity: _cameraEntities) {
            if (entity.canDestroy())
                continue;
            entity.onUpdate(dt);
        }

        for(BackgroundEntity entity: _bgEntities)
        {
            if(entity.canDestroy())
                continue;
            entity.onUpdate(dt);
        }

        tm.update(dt);
    }

    @Override
    public void onRender(Canvas canvas)
    {
        if(gm.isLoaded()) {
            for (BackgroundEntity entity : _bgEntities) {
                if (entity.canDestroy() || !entity.show)
                    continue;
                entity.onRender(canvas);
            }
            gm.render(canvas);
            im.render(canvas);
        }
        tm.render(canvas);
    }
}
