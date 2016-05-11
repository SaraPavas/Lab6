package com.example.sarapavas.lab6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sara Pavas on 8/05/2016.
 */
public class Peluchitos extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="PeluchitosBD";
    private static final int DATA_VERSION=1;

    String sqlCreate="CREATE TABLE Peluches(nombre TEXT PRIMARY KEY,ident INTEGER,cantidad INTEGER,precio INTEGER,venta INTEGER)";

    public Peluchitos(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        String nombre[] = {"IronMan", "ViudaNegra", "CapitanAmerica", "Hulk", "BrujaEscarlata", "SpiderMan"};
        int iden[] = {1, 2, 3, 4, 5, 6};
        int cantidad[] = {10, 10, 10, 10, 10, 10};
        int precio[] = {15000, 12000, 15000, 12000, 15000, 10000};
        //String pNombre = nombre[0];
        //int pCantidad, pPrecio, i;
        int i=0;
        if (db != null){
            for (i=0; i<=5; i++){
                String pNombre = nombre[i];
                int pIdent = iden[i];
                int pCantidad = cantidad[i];
                int pPrecio = precio[i];
                int pVenta = 0;

                db.execSQL("INSERT INTO Peluches(nombre,ident,cantidad,precio,venta)"+"VALUES('"+pNombre+"','"+pIdent+"','"+pCantidad+"','"+pPrecio+"','"+pVenta+"')");

            }
            //db.close();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Peluches");
        db.execSQL(sqlCreate);
    }
}
