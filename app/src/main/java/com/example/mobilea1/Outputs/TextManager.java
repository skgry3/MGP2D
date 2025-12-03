package com.example.mobilea1.Outputs;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.mobilea1.GameManager;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class TextManager {
    private int _screenWidth;
    private int _screenHeight;
    public Vector<Text> _textEntities = new Vector<>();
    FPSText FPS;
    public TextBox Results;
    public TextBox Turns;
    private boolean isLoaded;
    private TextManager() {

        _screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        _screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        float screenMidX = _screenWidth * 0.5f;
        float screenMidY = _screenHeight * 0.5f;

        FPS = new FPSText(new Vector2(100, 0), 75, Color.WHITE, Paint.Align.CENTER, true);
        Results = new TextBox("TEXT.",new Vector2(screenMidX, screenMidY), 75, Color.WHITE, Paint.Align.CENTER, true);
        Turns = new TextBox("TEXT", new Vector2(screenMidX, 0), 75, Color.WHITE, Paint.Align.CENTER, true);

        _textEntities.add(FPS);
        _textEntities.add(Results);
        _textEntities.add(Turns);

        for(Text txt: _textEntities)
        {
            txt.show = false;
            txt.active = true;
            txt.ignoreRaycast = true;
        }

        FPS.show = true;
        Turns.show = true;

        isLoaded = true;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    private static class BillPughSingleton {
        private static final TextManager INSTANCE = new TextManager();
    }
    public static TextManager getInstance()
    {
        return TextManager.BillPughSingleton.INSTANCE;
    }

    public void update(float dt) {

        if (_textEntities.isEmpty())
            return;

        for (Text txt : _textEntities) {
            if (txt.active)
                txt.onUpdate(dt);
        }

        _textEntities.removeIf(Text::canDestroy);

    }
    public void render(Canvas canvas)
    {
        for(Text txt: _textEntities)
        {
            if(txt.show)
                txt.onRender(canvas);
        }
    }

}
