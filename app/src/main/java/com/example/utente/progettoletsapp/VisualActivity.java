package com.example.utente.progettoletsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class VisualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);
        Toast.makeText(this, getIntent().getStringExtra("codice"), Toast.LENGTH_SHORT).show();
    }
}
