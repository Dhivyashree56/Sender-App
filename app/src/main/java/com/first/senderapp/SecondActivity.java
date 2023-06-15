package com.first.senderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {
    TextView textView;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView=findViewById(R.id.textViewID);
        backButton=findViewById(R.id.buttonID);

        //Creating intent that receive data from MainActivity
        Intent intent=getIntent();
        String text = intent.getStringExtra("readText");
        textView.setText(text);

        //Returning back to the MainActivity
        backButton.setOnClickListener(view -> finish());
    }
}