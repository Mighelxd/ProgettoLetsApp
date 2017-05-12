package com.example.utente.progettoletsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ciro on 11/05/2017.
 */

public class DatabasePosti extends SQLiteOpenHelper {

    public static final int LUN_TIP=8;
    public static final int LUN_MAX_STIP=6;

    public static final String NOMEDB="PostiDiInteresse";//nome database

    public DatabasePosti(Context context) {
        super(context,NOMEDB,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //stringhe contenenti le tabelle
        String tabTipo="CREATE TABLE tipo(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descrizione TEXT NOT NULL);";
        String tabSTipo="CREATE TABLE sottotipo(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descrizione TEXT NOT NULL," +
                "cod_tipo INTEGER," +
                "FOREIGN KEY(cod_tipo) REFERENCES tipo(codice));";
        String tabPosto="CREATE TABLE posto(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "latitudine INTEGER NOT NULL," +
                "longitudine INTEGER NOT NULL," +
                "dataSalvataggio TEXT NOT NULL," +
                "descrizione TEXT," +
                "Nstelle INTEGER," +
                "cod_Stipo INTEGER NOT NULL," +
                "FOREIGN KEY(cod_Stipo) REFERENCES sottotipo(codice));";
        String insTipo="INSERT INTO tipo (descrizione) VALUES" +
                "('Pub/Ristorante/Pizzeria'),('Club'),('Bar/Lounge bar')," +
                "('Localita' storico/culturale'),('Parcheggio'),('Negozio'),('Svago'),('Altro');";
        String insSTipo="INSERT INTO sottotipo (descrizione,cod_tipo) VALUES" +
                "('Pistorante',1),('Pub',1),('Pizzeria',1),('Ristorante/Pizzeria',1),('Paninoteca',1),('Fastfood',1)," +
                "('Discoteca',2),('Disco-bar',2)," +
                "('Bar',3),('Lounge-bar',3),('Enoteca',3)," +
                "('Museo',4),('Biblioteca',4),('Statua',4),('Palazzo',4),('Castello',4)," +
                "('Supermercato',6),('Macelleria',6),('Pescheria',6),('Salumeria',6),('Libreria',6),('Negozio abbigliamento',6)," +
                "('Cinema',7),('Sala giochi',7),('Centro scommesse',7),('Centro Sportivo',7);";

        db.execSQL(tabTipo);
        db.execSQL(tabSTipo);
        db.execSQL(tabPosto);
        db.execSQL(insTipo);
        db.execSQL(insSTipo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
