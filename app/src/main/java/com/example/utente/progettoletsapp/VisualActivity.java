package com.example.utente.progettoletsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VisualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);

        final TextView txtnom=(TextView) findViewById(R.id.txtnom),
                txttip=(TextView) findViewById(R.id.txttip),
                txtstip=(TextView) findViewById(R.id.txtstip),
                txtds=(TextView) findViewById(R.id.txtds),
                txtdescr=(TextView) findViewById(R.id.txtdescr);

        ImageView[] stlar={(ImageView)findViewById(R.id.stl1),(ImageView) findViewById(R.id.stl2),(ImageView) findViewById(R.id.stl3),(ImageView) findViewById(R.id.stl4),(ImageView) findViewById(R.id.stl5)};
        int[] ids={R.id.stl1,R.id.stl2,R.id.stl3,R.id.stl4,R.id.stl5};
    }

        public void buttonClicked(View v){
            if(v.getId()==R.id.bCanc){
                Toast.makeText(VisualActivity.this,"Premuto Cancella",Toast.LENGTH_LONG).show();
            }
            if(v.getId()==R.id.bMod){
                Toast.makeText(VisualActivity.this,"Premuto Modifica",Toast.LENGTH_LONG).show();
            }
            if(v.getId()==R.id.bPer){
                Toast.makeText(VisualActivity.this,"Premuto Percorso",Toast.LENGTH_LONG).show();
            }
    }
}
