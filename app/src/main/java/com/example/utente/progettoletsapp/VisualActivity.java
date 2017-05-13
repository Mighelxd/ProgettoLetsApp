package com.example.utente.progettoletsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VisualActivity extends AppCompatActivity {

    private DatabasePosti databasePosti;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String codice;
    private String nome;
    private String tipo;
    private String stipo;
    private String dataSalvataggio;
    private String descrizione;
    private String Nstelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);

        final TextView txtnom=(TextView) findViewById(R.id.txtnom);
        final TextView txttip=(TextView) findViewById(R.id.txttip);
        final TextView txtstip=(TextView) findViewById(R.id.txtstip);
        final TextView txtds=(TextView) findViewById(R.id.txtds);
        final TextView txtdescr=(TextView) findViewById(R.id.txtdescr);

        ImageView[] stlar={(ImageView)findViewById(R.id.stl1),(ImageView) findViewById(R.id.stl2),(ImageView) findViewById(R.id.stl3),(ImageView) findViewById(R.id.stl4),(ImageView) findViewById(R.id.stl5)};

        codice=getIntent().getStringExtra("codice");

        databasePosti=new DatabasePosti(this);
        db=databasePosti.getReadableDatabase();

        String queryStr="SELECT p.nome,t.descrizione,st.descrizione,p.dataSalvataggio,p.descrizione,p.Nstelle " +
                "FROM posto AS p,tipo AS t,sottotipo AS st " +
                "WHERE p.cod_Stipo=st.codice AND st.cod_tipo=t.codice AND p.codice="+codice+"; ";

        cursor=db.rawQuery(queryStr,null);
        if(cursor.moveToFirst())
        {
            nome=cursor.getString(0);
            tipo=cursor.getString(1);
            stipo=cursor.getString(2);
            dataSalvataggio=cursor.getString(3);
            descrizione=cursor.getString(4);
            Nstelle=cursor.getString(5);
        }
        txtnom.setText(nome);
        txttip.setText(txttip.getText().toString()+" "+tipo);
        txtstip.setText(txtstip.getText().toString()+" "+stipo);
        txtds.setText(txtds.getText().toString()+" "+dataSalvataggio);
        txtdescr.setText(txtdescr.getText().toString()+" "+descrizione);

        for(int i=5;i>Integer.parseInt(Nstelle);--i)
        {
            stlar[i-1].setVisibility(View.INVISIBLE);
        }
    }

        public void buttonClicked(View v){
            if(v.getId()==R.id.bCanc){
                new AlertDialog.Builder(VisualActivity.this)
                        .setTitle("Conferma")
                        .setMessage("Sei sicuro di voler eliminare questa voce?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String queryDelete="DELETE FROM posti WHERE codice="+codice+";";
                                db.execSQL(queryDelete);
                                finish();
                            }
                        })
                        .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            if(v.getId()==R.id.bMod){
                Intent intent=new Intent(VisualActivity.this,SalvaLuogoActivity.class);
                intent.putExtra("codice",codice);
                intent.putExtra("codRichiesta","2");
            }
            if(v.getId()==R.id.bPer){
                Toast.makeText(VisualActivity.this,"Premuto Percorso",Toast.LENGTH_LONG).show();
            }
    }
}
