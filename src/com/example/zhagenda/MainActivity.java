package com.example.zhagenda;

import android.os.Bundle;
import android.app.Activity; 
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, ActivityMap.class);
        startActivity(i);
    }
 
    
}
