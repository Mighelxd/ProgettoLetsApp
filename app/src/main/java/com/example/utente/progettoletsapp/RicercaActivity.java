package com.example.utente.progettoletsapp;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RicercaActivity extends AppCompatActivity {

    private String testoRic;
    private EditText edRic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        edRic=(EditText) findViewById(R.id.editText);
        edRic.setText(getIntent().getStringExtra("edRic"));
        TextView.OnEditorActionListener ls1=new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                bSearch();
                return true;
            }
        };
        edRic.setOnEditorActionListener(ls1);
        bSearch();

    }
    public void buttonClicked(View v){
        bSearch();
    }
    public void bSearch(){
        edRic=(EditText) findViewById(R.id.editText);
        testoRic=edRic.getText().toString();
        String queryRic="SELECT p.codice,p.nome FROM posto p, sottotipo s, tipo t" +
                "WHERE p.cod_Stipo=s.codice AND s.cod_tipo=t.codice AND (p.nome LIKE '%"+testoRic+"%'" +
                "OR p.descrizione LIKE '%"+testoRic+"%' OR s.descrizione LIKE '%"+testoRic+"%' OR t.descrizione LIKE '%"+testoRic+"%')" +
                "ORDER BY p.Nstelle;";
    }

}
