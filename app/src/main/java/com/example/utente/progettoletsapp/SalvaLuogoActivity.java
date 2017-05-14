package com.example.utente.progettoletsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class SalvaLuogoActivity extends Activity {

    private Spinner spinnerTipo, spinnerSTipo;
    private EditText eDesc, eNome;
    private DatabasePosti databasePosti;
    private SQLiteDatabase db;
    private Cursor cursor, cursor2;

    private String codiciST[];

    private int indexST;

    private String nome;
    private String latitudine;
    private String longitudine;
    private String dataSalvataggio;
    private String descrizione;
    private String Nstelle;
    private String cod_Stipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salva_luogo);
        Nstelle="1";
        cod_Stipo="1";
        eDesc = (EditText) findViewById(R.id.edtxtdescr);
        eNome = (EditText) findViewById(R.id.edtxtnom);
        spinnerTipo = (Spinner) findViewById(R.id.spintipo);
        spinnerSTipo = (Spinner) findViewById(R.id.spinstipo);
        String tipi[] = new String[DatabasePosti.LUN_TIP];
        databasePosti = new DatabasePosti(this);
        db = databasePosti.getReadableDatabase();

        String queryTipi = "SELECT descrizione FROM tipo;";

        cursor = db.rawQuery(queryTipi, null);

        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                tipi[i++] = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        spinnerTipo.setAdapter(new ArrayAdapter<String>(SalvaLuogoActivity.this, android.R.layout.simple_spinner_dropdown_item, tipi));

        //ascoltatore che permette allo spinner sottotipo di mostrare la lista inerente al tipo selezionato
        Spinner.OnItemSelectedListener ls = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inizSTipo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinnerTipo.setOnItemSelectedListener(ls);

        Spinner.OnItemSelectedListener ls2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cod_Stipo = codiciST[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerSTipo.setOnItemSelectedListener(ls2);

        if (getIntent().getIntExtra("codRichiesta", 0) == 2) inizMod();

    }


    public void imageClickedStella(View v) {
        ImageView[] stlar = {(ImageView) findViewById(R.id.stl1), (ImageView) findViewById(R.id.stl2), (ImageView) findViewById(R.id.stl3), (ImageView) findViewById(R.id.stl4), (ImageView) findViewById(R.id.stl5)};
        int[] ids = {R.id.stl1, R.id.stl2, R.id.stl3, R.id.stl4, R.id.stl5};
        for (int i = 0; i < 5; ++i)
            if (v.getId() == ids[i]) {
                Nstelle = Integer.toString(i + 1);
                for (int j = i; j >= 0; j--) stlar[j].setImageResource(R.drawable.stellagialla);
                for (int k = i + 1; k < 5; k++) stlar[k].setImageResource(R.drawable.stellabianca);
            }

    }

    public void buttonClickedSave(View v) {
        nome = eNome.getText().toString();
        descrizione = eDesc.getText().toString();
        if (nome.equals(""))
            Toast.makeText(SalvaLuogoActivity.this, "Devi inserire un nome!", Toast.LENGTH_SHORT).show();
        else {
            switch (getIntent().getIntExtra("codRichiesta", 0)) {
                case 1:
                    primoSalva();
                    break;
                case 2:
                    modifica();
                    break;
            }
        }
    }

    private void primoSalva() {
        latitudine = getIntent().getStringExtra("latitudine");
        longitudine = getIntent().getStringExtra("longitudine");
        String[] mesi={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String cd = new Date().toString();
        int mesei=1;
        for(int i=0;i<11;++i)
        {
            if(cd.substring(4,7).equals(mesi[i])) mesei=i+1;
        }
        String meses=String.valueOf(mesei);
        if(mesei>=1&&mesei<=9) meses="0"+meses;
        dataSalvataggio = cd.substring(8,10)+"-"+meses+"-"+cd.substring(30,34)+cd.substring(10,19);

        String queryInsPosto = "INSERT INTO posto " +
                "(nome,latitudine,longitudine,dataSalvataggio,descrizione,Nstelle,cod_Stipo)" +
                "VALUES ('" + nome + "'," + latitudine + "," + longitudine + ",'" + dataSalvataggio + "','" + descrizione + "'," + Nstelle + "," + cod_Stipo + ");";
        db.execSQL(queryInsPosto);
        Toast.makeText(this, "Posto salvato.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void modifica() {
        String queryMod="UPDATE posto " +
                "SET nome='"+nome+"', latitudine="+latitudine+", longitudine="+longitudine+", dataSalvataggio='"+dataSalvataggio+"', " +
                "descrizione='"+descrizione+"', Nstelle="+Nstelle+", cod_Stipo="+cod_Stipo+" " +
                "WHERE codice="+getIntent().getStringExtra("codice")+";";
        db.execSQL(queryMod);
        Toast.makeText(this, "Informazioni su "+nome+" modificate.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void inizMod() {
        String queryInizMod = "SELECT p.nome, p.latitudine, p.longitudine, p.dataSalvataggio, p.descrizione, p.Nstelle, s.descrizione, t.descrizione, s.codice " +
                "FROM posto AS p, sottotipo AS s, tipo AS t " +
                "WHERE p.cod_Stipo=s.codice AND s.cod_tipo=t.codice AND p.codice=" + getIntent().getStringExtra("codice") + ";";
        Cursor cursor1 = db.rawQuery(queryInizMod, null);
        if (cursor1.moveToFirst()) {
            nome = cursor1.getString(0);
            latitudine = cursor1.getString(1);
            longitudine = cursor1.getString(2);
            dataSalvataggio = cursor1.getString(3);
            descrizione = cursor1.getString(4);
            Nstelle = cursor1.getString(5);
            String sDesc = cursor1.getString(6);
            String tDesc = cursor1.getString(7);
            cod_Stipo=cursor1.getString(8);
            int i=0;
            eNome.setText(nome);
            eDesc.setText(descrizione);
            ImageView[] stlAr = {(ImageView) findViewById(R.id.stl1), (ImageView) findViewById(R.id.stl2), (ImageView) findViewById(R.id.stl3), (ImageView) findViewById(R.id.stl4), (ImageView) findViewById(R.id.stl5)};
            stlAr[Integer.parseInt(Nstelle)-1].performClick();
            for(i = 0; i <= spinnerTipo.getAdapter().getCount() && !spinnerTipo.getItemAtPosition(i).toString().equals(tDesc); ++i);
            spinnerTipo.setSelection(i,true);
            inizSTipo(i);

            for(i = 0;  i<= spinnerSTipo.getAdapter().getCount() && !spinnerSTipo.getItemAtPosition(i).toString().equals(sDesc); ++i);
            indexST=i;

        }

    }
    public void inizSTipo(int position)
    {   TextView txt = (TextView) findViewById(R.id.txtstipo);
        spinnerSTipo.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);
        String querySTipi = "SELECT codice,descrizione FROM sottotipo WHERE cod_tipo=" + Integer.toString(position + 1) + ";";
        String stipi[] = new String[DatabasePosti.LUN_STIP[position]];
        codiciST = new String[DatabasePosti.LUN_STIP[position]];
        cursor2 = db.rawQuery(querySTipi, null);
        if (cursor2.moveToFirst()) {
            int j = 0;
            do {
                codiciST[j] = cursor2.getString(0);
                stipi[j++] = cursor2.getString(1);
            } while (cursor2.moveToNext());
            spinnerSTipo.setAdapter(new ArrayAdapter<String>(SalvaLuogoActivity.this, android.R.layout.simple_spinner_dropdown_item, stipi));
        }
        if (DatabasePosti.LUN_STIP[position] == 1) {
            spinnerSTipo.setVisibility(View.INVISIBLE);
            txt.setVisibility(View.INVISIBLE);
        }
        else
            spinnerSTipo.setSelection(indexST,true);
    }
}
