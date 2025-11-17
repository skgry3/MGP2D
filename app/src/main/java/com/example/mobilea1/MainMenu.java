package com.example.mobilea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameScene;

public class MainMenu extends Activity implements View.OnClickListener {

    private Button helpBtn;
    private Button playBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mainmenu);

        helpBtn = findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(this);

        playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v == helpBtn)
        {
            startActivity(new Intent().setClass(this, HelpMenu.class));
        }
        else if(v == playBtn)
        {
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
        }

    }
}
