package com.example.utente.progettoletsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ciro on 15/05/2017.
 */

public class SetSuggerimenti {



    public static String[] SUGG(Context context){
        String sugg[];
        DatabasePosti databasePosti = new DatabasePosti(context);
        SQLiteDatabase db = databasePosti.getReadableDatabase();

        String querySuggP = "SELECT nome FROM posto;";
        String querySuggS = "SELECT descrizione FROM sottotipo;";
        String querySuggT = "SELECT descrizione FROM tipo;";

        Cursor cursor = db.rawQuery(querySuggP, null);
        String postiTr[] = new String[cursor.getCount()];
        int i = 0;

        if (cursor.moveToFirst())
            do {
                postiTr[i++] = cursor.getString(0);
            } while (cursor.moveToNext());

        cursor = db.rawQuery(querySuggS, null);
        String stipiTr[] = new String[cursor.getCount()];
        int j = 0;

        if (cursor.moveToFirst())
            do {
                stipiTr[j++] = cursor.getString(0);
            } while (cursor.moveToNext());

        cursor = db.rawQuery(querySuggT, null);
        String tipiTr[] = new String[cursor.getCount()];
        int k = 0;

        if (cursor.moveToFirst())
            do {
                tipiTr[k++] = cursor.getString(0);
            } while (cursor.moveToNext());

        sugg = new String[i+j+k];
        int z = 0;
        accoda(postiTr,sugg,i,z);
        z+=i;
        accoda(stipiTr,sugg,j,z);
        z+=j;
        accoda(tipiTr,sugg,k,z);

        return sugg;
    }

    private static void accoda(String s[], String sugg[], int num, int z) {
        for (int i=0;i<num;++i) sugg[z++]=s[i];

    }
}
