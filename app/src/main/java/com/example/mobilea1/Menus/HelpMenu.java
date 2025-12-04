package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import com.example.mobilea1.R;

public class HelpMenu extends Activity implements View.OnClickListener  {

    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpmenu);

        getWindow().setDecorFitsSystemWindows(false);

        WindowInsetsController controller = getWindow().getInsetsController();
        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars());
            controller.setSystemBarsBehavior(
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            // open your help / units menu
            startActivity(new Intent().setClass(this, MainMenu.class));

        }
    }
}
