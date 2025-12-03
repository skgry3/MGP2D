package com.example.mobilea1;

import android.graphics.Canvas;

import com.example.mobilea1.mgp2dCore.Vector2;

public class CameraManager {
    private static Camera MainCam;
    CameraManager()
    {
        MainCam = new Camera();
        MainCam.isUI = true;
        MainCam.active = true;
        MainCam.setPosition(new Vector2(0,0));
    }
    private static class BillPughSingleton {
        private static final CameraManager INSTANCE = new CameraManager();
    }
    public static CameraManager getInstance()
    {
        return CameraManager.BillPughSingleton.INSTANCE;
    }
    public Camera getMainCam()
    {
        return MainCam;
    }

    public void update(float dt)
    {
        MainCam.onUpdate(dt);
    }
}
