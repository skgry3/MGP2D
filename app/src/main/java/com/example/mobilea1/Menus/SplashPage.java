package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import com.example.mobilea1.R;

public class SplashPage extends Activity implements View.OnClickListener  {

    private Button StartBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.splashpage);

        getWindow().setDecorFitsSystemWindows(false);

        WindowInsetsController controller = getWindow().getInsetsController();
        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars());
            controller.setSystemBarsBehavior(
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }
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
