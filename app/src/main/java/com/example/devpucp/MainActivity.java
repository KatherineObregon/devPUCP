package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void empecemos(View view){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
}