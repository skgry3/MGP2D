package com.example.mobilea1.Outputs;
import android.graphics.Canvas;
import android.graphics.Color;
import java.util.Vector;

public class TextManager {
    Vector<Text> _textEntities = new Vector<>();
    Text FPS;
    private TextManager() {
        FPS = new Text(Color.WHITE);

        _textEntities.add(FPS);

        for(Text txt: _textEntities)
        {
            txt.show = false;
            txt.active = true;
        }

        FPS.show = true;
    }
    private static class BillPughSingleton {
        private static final TextManager INSTANCE = new TextManager();
    }
    public static TextManager getInstance()
    {
        return TextManager.BillPughSingleton.INSTANCE;
    }

    public void update(float dt)
    {
        for(Text txt: _textEntities)
        {
            if(txt.active)
               txt.onUpdate(dt);
        }
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
