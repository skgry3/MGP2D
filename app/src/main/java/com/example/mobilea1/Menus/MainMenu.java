package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mobilea1.R;
import com.example.mobilea1.Scenes.MainGameScene;
import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameScene;

public class MainMenu extends Activity implements View.OnClickListener {

    private Button HelpBtn;
    private Button QuitBtn;
    private ImageButton PlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        HelpBtn = findViewById(R.id.helpBtn);
        QuitBtn = findViewById(R.id.quitBtn);
        PlayBtn = findViewById(R.id.playBtn);

        HelpBtn.setOnClickListener(this);
        QuitBtn.setOnClickListener(this);
        PlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == HelpBtn) {
            // open your help / units menu
            startActivity(new Intent().setClass(this, HelpMenu.class));

        } else if (v == PlayBtn) {
            // start the game
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);

        } else if (v == QuitBtn) {
            // quit the game (close all activities in this task)
            finishAffinity();
        }
    }
}