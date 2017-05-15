package com.example.utente.progettoletsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ciro on 11/05/2017.
 */

public class DatabasePosti extends SQLiteOpenHelper {

    public static final int LUN_TIP=9;
    public static final int LUN_STIP[]={6,2,3,5,1,6,4,1,4};

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
                "descrizione TEXT," +
                "cod_tipo INTEGER," +
                "FOREIGN KEY(cod_tipo) REFERENCES tipo(codice));";
        String tabPosto="CREATE TABLE posto(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "latitudine REAL NOT NULL," +
                "longitudine REAL NOT NULL," +
                "dataSalvataggio TEXT," +
                "descrizione TEXT," +
                "Nstelle INTEGER," +
                "cod_Stipo INTEGER," +
                "FOREIGN KEY(cod_Stipo) REFERENCES sottotipo(codice));";
        String insTipo="INSERT INTO tipo (descrizione) VALUES" +
                "('Pub/Ristorante/Pizzeria'),('Club'),('Bar/Lounge bar')," +
                "('Località storico/culturale'),('Parcheggio'),('Negozio'),('Svago'),('Altro'),('Scuola');";
        String insSTipo="INSERT INTO sottotipo (descrizione,cod_tipo) VALUES" +
                "('Ristorante',1),('Pub',1),('Pizzeria',1),('Ristorante/Pizzeria',1),('Paninoteca',1),('Fastfood',1)," +
                "('Discoteca',2),('Disco-bar',2)," +
                "('Bar',3),('Lounge-bar',3),('Enoteca',3)," +
                "('Museo',4),('Biblioteca',4),('Statua',4),('Palazzo',4),('Castello',4)," +
                "('Parcheggio',5)," +
                "('Supermercato',6),('Macelleria',6),('Pescheria',6),('Salumeria',6),('Libreria',6),('Negozio abbigliamento',6)," +
                "('Cinema',7),('Sala giochi',7),('Centro scommesse',7),('Centro sportivo',7)," +
                "('Altro',8),('Scuola dell infanzia',9),('Scuola primaria',9),('Scuola secondaria di primo grado',9),('Scuola secondaria di secondo grado o superiore',9);";

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
