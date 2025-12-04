package com.example.mobilea1.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilea1.R;

public class HelpMenu extends Activity implements View.OnClickListener  {

    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpmenu);

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
