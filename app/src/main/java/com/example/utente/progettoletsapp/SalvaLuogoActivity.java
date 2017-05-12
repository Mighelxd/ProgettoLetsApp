package com.example.utente.progettoletsapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SalvaLuogoActivity extends Activity {

    private Spinner spinner;
    private DatabasePosti databasePosti;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salva_luogo);

        spinner=(Spinner) findViewById(R.id.spintipo);

        String tipi[]=new String[DatabasePosti.LUN_TIP];

        databasePosti=new DatabasePosti(this);

        db=databasePosti.getReadableDatabase();

        String queryTipi="SELECT descrizione FROM tipi";

        cursor=db.rawQuery(queryTipi,null);

        if(cursor.moveToFirst()) {
            int i=0;
            do {
                tipi[i++]=cursor.getString(0);
            } while (cursor.moveToNext());
        }

        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipi));





    }





    public void buttonClicked2(View v) {
    }
}
