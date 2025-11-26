package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilea1.R;
import com.example.mobilea1.Scenes.MainGameScene;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameScene;

public class MainMenu extends Activity implements View.OnClickListener {

    private Button HelpBtn;
    private Button PlayBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mainmenu);

        HelpBtn = findViewById(R.id.helpBtn);
        HelpBtn.setOnClickListener(this);

        PlayBtn = findViewById(R.id.playBtn);
        PlayBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v == HelpBtn)
        {
            startActivity(new Intent().setClass(this, HelpMenu.class));
        }
        else if(v == PlayBtn)
        {
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
        }

    }
}
