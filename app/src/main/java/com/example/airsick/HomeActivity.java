package com.example.airsick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        Button buttonMap = (Button) findViewById(R.id.buttonMap);

        buttonMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setContentView(R.layout.activity_map);
            }
        });

    }
}