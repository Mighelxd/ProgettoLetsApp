package com.example.utente.progettoletsapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalvaLuogoActivity extends Activity {

    private Spinner spinnerTipo,spinnerSTipo;
    private DatabasePosti databasePosti;
    private SQLiteDatabase db;
    private Cursor cursor,cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salva_luogo);

        spinnerTipo=(Spinner) findViewById(R.id.spintipo);

        String tipi[]=new String[DatabasePosti.LUN_TIP];

        databasePosti=new DatabasePosti(this);

        db=databasePosti.getReadableDatabase();

        String queryTipi="SELECT descrizione FROM tipo;";

        cursor=db.rawQuery(queryTipi,null);
        /* latitudine e longitudine
        Double lat=Double.parseDouble(getIntent().getStringExtra("latitudine"));
        Double lon=Double.parseDouble(getIntent().getStringExtra("longitudine"));
        */
        if(cursor.moveToFirst()) {
            int i=0;
            do {
                tipi[i++]=cursor.getString(0);
            } while (cursor.moveToNext());
        }

        spinnerTipo.setAdapter(new ArrayAdapter<String>(SalvaLuogoActivity.this,android.R.layout.simple_spinner_dropdown_item,tipi));


        Spinner.OnItemSelectedListener ls=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSTipo=(Spinner) findViewById(R.id.spinstipo);
                if(DatabasePosti.LUN_STIP[position]!=0) {
                    spinnerSTipo.setVisibility(View.VISIBLE);
                    String querySTipi="SELECT descrizione FROM sottotipo WHERE cod_tipo="+Integer.toString(position+1)+";";
                    String stipi[] = new String[DatabasePosti.LUN_STIP[position]];
                    cursor2=db.rawQuery(querySTipi,null);
                    if(cursor2.moveToFirst()) {
                        int j=0;
                        do {
                            stipi[j++]=cursor2.getString(0);
                        } while (cursor2.moveToNext());
                    }
                    spinnerSTipo.setAdapter(new ArrayAdapter<String>(SalvaLuogoActivity.this,android.R.layout.simple_spinner_dropdown_item,stipi));
                }
                else
                    spinnerSTipo.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinnerTipo.setOnItemSelectedListener(ls);




    }


    public void imageClickedStella(View v)
    {
        ImageView[] stlar={(ImageView)findViewById(R.id.stl1),(ImageView) findViewById(R.id.stl2),(ImageView) findViewById(R.id.stl3),(ImageView) findViewById(R.id.stl4),(ImageView) findViewById(R.id.stl5)};
        int[] ids={R.id.stl1,R.id.stl2,R.id.stl3,R.id.stl4,R.id.stl5};
        for(int i=0;i<5;++i)
            if(v.getId()==ids[i]) {
                for(int j=i;j>=0;j--) stlar[j].setImageResource(R.drawable.stellagialla);
                for(int k=i+1;k<5;k++) stlar[k].setImageResource(R.drawable.stellabianca);
            }
    }

    public void buttonClickedSave(View v)
    {
    }
}
