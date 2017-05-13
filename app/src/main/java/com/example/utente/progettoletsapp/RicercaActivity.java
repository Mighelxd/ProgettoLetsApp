package com.example.utente.progettoletsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

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
        testoRic=edRic.getText().toString();

        String queryRic="SELECT p.codice,p.nome FROM posto p, sottotipo s, tipo t" +
                "WHERE p.cod_Stipo=s.codice AND s.cod_tipo=t.codice AND (p.nome LIKE '%"+testoRic+"%'" +
                "OR p.descrizione LIKE '%"+testoRic+"%' OR s.descrizione LIKE '%"+testoRic+"%' OR t.descrizione LIKE '%"+testoRic+"%')" +
                "ORDER BY p.Nstelle;";

    }
    public void buttonClicked(View v){

    }
}
