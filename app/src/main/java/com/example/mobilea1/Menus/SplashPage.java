package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilea1.R;

public class SplashPage extends Activity implements View.OnClickListener  {

    private Button StartBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.splashpage);

        StartBtn = findViewById(R.id.tapOverlay);
        StartBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        if(v == StartBtn)
        {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }

    }
}
