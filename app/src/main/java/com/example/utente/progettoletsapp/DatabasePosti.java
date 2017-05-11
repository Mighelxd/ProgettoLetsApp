package com.example.utente.progettoletsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ciro on 11/05/2017.
 */

public class DatabasePosti extends SQLiteOpenHelper {

    public static final String NOMEDB="PostiDiInteresse";//nome database

    public DatabasePosti(Context context) {
        super(context,NOMEDB,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabTipo="CREATE TABLE tipo(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descrizione TEXT NOT NULL);";
        String tabSTipo="CREATE TABLE sottotipo(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descrizione TEXT NOT NULL," +
                "cod_tipo INTEGER NOT NULL," +
                "FOREIGN KEY(cod_tipo) REFERENCES tipo(codice));";
        String tabPosto="CREATE TABLE posto(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "latitudine INTEGER NOT NULL," +
                "longitudine INTEGER NOT NULL," +
                "dataSalvataggio TEXT NOT NULL," +
                "Nstelle INTEGER," +
                "cod_Stipo INTEGER NOT NULL," +
                "FOREIGN KEY(cod_Stipo) REFERENCES sottotipo(codice));";

        db.execSQL(tabTipo);
        db.execSQL(tabSTipo);
        db.execSQL(tabPosto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
