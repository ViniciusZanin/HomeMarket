package com.ufabc.kleinzanin.homemarket.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.ufabc.kleinzanin.homemarket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 30/04/2015.
 */
public class ListaComprasDAO extends SQLiteOpenHelper{
    private static ListaComprasDAO dao;
    private List<ListaCompras> listaCompras;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = ListaComprasDAO.class.getSimpleName();

    public ListaComprasDAO(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }
    public static ListaComprasDAO newInstance(Context c) {
        if (dao == null) {
            dao = new ListaComprasDAO(c);
            dao.init();
        }


        return dao;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryStr1 = context.getString(R.string.create_table_produtos_query);
        String queryStr2 = context.getString(R.string.create_table_mercados_query);
        String queryStr3 = context.getString(R.string.create_table_receitas_query);
        String queryStr4 = context.getString(R.string.create_table_ingredientes_query);
        String queryStr5 = context.getString(R.string.create_table_listacompras_query);
        String queryStr6 = context.getString(R.string.create_table_listacompras_produtos_query);
        try {
            db.execSQL(queryStr1);
            db.execSQL(queryStr2);
            db.execSQL(queryStr3);
            db.execSQL(queryStr4);
            db.execSQL(queryStr5);
            db.execSQL(queryStr6);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to create database", e);
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: here we must implement a proper way to migrate the schema, version by version
        // currently, it only recreates the database
        String queryStr = context.getString(R.string.drop_table_listacompras_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    private void init() {
        listaCompras = new ArrayList<>();
        dao.listfirst();
    }

    public boolean add(ListaCompras i) {
        String queryStr = context.getString(R.string.insert_listacompras_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, i.getData());
            statement.bindDouble(2, i.getPreco());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add ingredientes in the database", e);
            status = false;
        }

        return status;
    }

    public boolean remove(int position) {
        String queryStr = context.getString(R.string.remove_listacompras_query);
        boolean status = true;
        ArrayList<ListaCompras>listaCompras;
        dao = ListaComprasDAO.newInstance(context);
        listaCompras = dao.list();
        ListaCompras listaCompra = listaCompras.get(position);

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,listaCompra.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove Lista in the database", e);
            status = false;
        }

        return status;
    }

    public int size() {
        String queryStr = context.getString(R.string.count_listacompras_query);
        int count = -1;

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count products in the database", e);
        }

        return count;
    }
    public ListaCompras getItemAt(int position) {
        String queryStr = "SELECT * FROM listacompras WHERE = ";
        ListaCompras listaCompras = new ListaCompras();


        try {
            Cursor cursor = db.rawQuery(queryStr + position, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                listaCompras.setID(cursor.getInt(0));
                listaCompras.setData(cursor.getString(1));
                listaCompras.setPreco(cursor.getDouble(2));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get ingrediente in the database", e);
        }

        return listaCompras;
    }

    public ArrayList<ListaCompras> list() {
        ArrayList<ListaCompras> listaCompras = new ArrayList<>();
        String queryStr = context.getString(R.string.list_listacompras_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                ListaCompras listaCompra = new ListaCompras();
                listaCompra.setID(cursor.getInt(0));
                listaCompra.setData(cursor.getString(1));
                listaCompra.setPreco(cursor.getDouble(2));
                listaCompras.add(listaCompra);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list ingredientes from the database", e);
        }

        return listaCompras;
    }
    public ListaCompras listfirst() {
        ListaCompras listaCompras = new ListaCompras();
        String queryStr = context.getString(R.string.select_listacompras_first_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                listaCompras.setID(cursor.getInt(0));
                listaCompras.setData(cursor.getString(1));
                listaCompras.setPreco(cursor.getDouble(2));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get lista first in the database", e);
        }
        if(listaCompras == null){
            listaCompras.setData("0");
            listaCompras.setPreco(0);
            add(listaCompras);
        }

        return listaCompras;
    }

    public ListaCompras getMaxID() {
        String queryStr = context.getString(R.string.get_listacompras_max_query);
        ListaCompras listaCompras = new ListaCompras();


        try {
            Cursor cursor = db.rawQuery(queryStr, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                listaCompras.setID(cursor.getInt(0));
                listaCompras.setData(cursor.getString(1));
                listaCompras.setPreco(cursor.getDouble(2));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get ingrediente in the database", e);
        }

        return listaCompras;
    }

    public boolean editpreco (ListaCompras i){
        String queryStr = context.getString(R.string.edit_listacompras_preco_query);
        boolean status = true;
        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, i.getData());
            statement.bindDouble(2, i.getPreco());
            statement.bindLong(3, i.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add ingredientes in the database", e);
            status = false;
        }
        return status;
    }
}
