package com.sampleapp.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sampleapp.MainActivity;
import com.sampleapp.R;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}