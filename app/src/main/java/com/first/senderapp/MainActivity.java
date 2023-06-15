package com.first.senderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//import android.annotation.SuppressLint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.InputStreamReader;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    EditText text;
    Button write;
    Button read;
    Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editTextID);
        write = findViewById(R.id.writeID);
        read = findViewById(R.id.readID);
        clear = findViewById(R.id.clearID);

        //Getting User permission in runtime
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        //Using explicit intents to send data over other apps
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = text.getText().toString();

                //Creating an intent that send data to other apps
                Intent sendIntent = new Intent();
                //Apps with ACTION_VIEW action can receive the sent data
                sendIntent.setAction(Intent.ACTION_VIEW);
                //Sending the user entered data in textbox
                sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                //Type of data sent(i.e., Text Data)
                sendIntent.setType("text/plain");
                //Start activity and result will be return back
                startActivityForResult(sendIntent, 1);

                Toast.makeText(getBaseContext() , "DATA IS SEND TO READ APP", Toast.LENGTH_LONG ).show();
            }
        });

        //Using explicit intent to send data from MainActivity to secondActivity
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data;
                StringBuilder buff = new StringBuilder();

                //Data is read from a textfile in the storage
                try{
                    File file = new File(Environment.getExternalStorageDirectory(), "NewFile.txt");
                    //File file = new File("/sdcard/input.txt");

                    FileInputStream fin = new FileInputStream(file);
                    BufferedReader br = new BufferedReader( new InputStreamReader(fin));

                    while ( ( data = br.readLine() ) != null ){
                        buff.append(data);
                    }

                    String res = buff.toString();

                    //Creating an intent that can send data from MainActivity to SecondActivity
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("readText", res);
                    startActivity(intent);

                    br.close();
                    fin.close();

                    Toast.makeText(getBaseContext() , "DATA IS READ", Toast.LENGTH_LONG ).show();
                }
                catch ( Exception e ){
                    Toast.makeText(getBaseContext() , e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }
        });

        //clear the textbox
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
            }
        });
    }

    //Result of the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                Toast.makeText(getBaseContext() , "WELCOME MESSAGE IS RECEIVED", Toast.LENGTH_LONG ).show();
                text.setText(result);
            }
            if(resultCode == RESULT_CANCELED){
                text.setText("Nothing is selected");
            }
        }
    }
}