package com.example.utente.progettoletsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RicercaActivity extends AppCompatActivity {

    DatabasePosti databasePosti;
    SQLiteDatabase db;

    private String testoRic;
    private EditText edRic;


    private String nomiRic[];
    private String codiciRic[];
    private int numVoci;
    private int codRichiesta;

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        codRichiesta = getIntent().getIntExtra("codRichiesta", 1);
        edRic = (EditText) findViewById(R.id.editText);

        edRic.setText(getIntent().getStringExtra("edRic"));
        TextView.OnEditorActionListener ls1 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                bSearch();
                return true;
            }
        };
        edRic.setOnEditorActionListener(ls1);

        databasePosti = new DatabasePosti(RicercaActivity.this);
        db = databasePosti.getReadableDatabase();

        lista = (ListView) findViewById(R.id.listaPosti);
        numVoci = 0;
        bSearch();
        AdapterView.OnItemClickListener ls2 = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(RicercaActivity.this, VisualActivity.class);
                i.putExtra("codice", codiciRic[position]);
                startActivity(i);
            }
        };
        lista.setOnItemClickListener(ls2);

    }

    public void buttonClicked(View v) {
        bSearch();
    }

    public void bSearch() {
        switch (codRichiesta) {
            case 1:
                ric();
                break;
            case 2:
                all();
                break;
            case 3:
                liked();
                break;
        }

    }

    public void ric() {
        testoRic = edRic.getText().toString();
        String queryRic = "SELECT p.codice,p.nome FROM posto AS p, sottotipo AS s, tipo AS t " +
                "WHERE p.cod_Stipo=s.codice AND s.cod_tipo=t.codice AND (p.nome LIKE '%" + testoRic + "%' " +
                "OR p.descrizione LIKE '%" + testoRic + "%' OR s.descrizione LIKE '%" + testoRic + "%' OR t.descrizione LIKE '%" + testoRic + "%') " +
                "ORDER BY p.Nstelle;";

        Cursor cursor = db.rawQuery(queryRic, null);
        numVoci = 0;
        if (cursor.getCount() > 0) {
            nomiRic = new String[cursor.getCount()];
            codiciRic = new String[cursor.getCount()];
            if (cursor.moveToFirst())
                do {
                    codiciRic[numVoci] = cursor.getString(0);
                    nomiRic[numVoci++] = cursor.getString(1);

                } while (cursor.moveToNext());


            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
        } else {
            numVoci = 0;
            nomiRic = new String[numVoci];
            codiciRic = new String[numVoci];
            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
            Toast.makeText(RicercaActivity.this, "Nessun posto trovato!", Toast.LENGTH_SHORT).show();
        }
    }

    public void all() {
        String queryRic = "SELECT p.codice,p.nome FROM posto as p; ";
        Cursor cursor = db.rawQuery(queryRic, null);
        numVoci = 0;
        if (cursor.getCount() > 0) {
            nomiRic = new String[cursor.getCount()];
            codiciRic = new String[cursor.getCount()];
            if (cursor.moveToFirst())
                do {
                    codiciRic[numVoci] = cursor.getString(0);
                    nomiRic[numVoci++] = cursor.getString(1);

                } while (cursor.moveToNext());


            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
        } else {
            numVoci = 0;
            nomiRic = new String[numVoci];
            codiciRic = new String[numVoci];
            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
            Toast.makeText(RicercaActivity.this, "Nessun posto trovato!", Toast.LENGTH_SHORT).show();
        }
    }

    public void liked() {
        String queryRic = "SELECT p.codice,p.nome FROM posto as p " +
                "WHERE p.Nstelle=4 OR p.Nstelle=5 "+
                "ORDER BY p.Nstelle;";
        Cursor cursor = db.rawQuery(queryRic, null);
        numVoci = 0;
        if (cursor.getCount() > 0) {
            nomiRic = new String[cursor.getCount()];
            codiciRic = new String[cursor.getCount()];
            if (cursor.moveToFirst())
                do {
                    codiciRic[numVoci] = cursor.getString(0);
                    nomiRic[numVoci++] = cursor.getString(1);

                } while (cursor.moveToNext());


            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
        } else {
            numVoci = 0;
            nomiRic = new String[numVoci];
            codiciRic = new String[numVoci];
            lista.setAdapter(new ArrayAdapter<String>(RicercaActivity.this, android.R.layout.simple_list_item_1, nomiRic));
            Toast.makeText(RicercaActivity.this, "Nessun posto trovato!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (codRichiesta) {
            case 1:
                ric();
                break;
            case 2:
                all();
                break;
            case 3:
                liked();
                break;
        }
    }
}
