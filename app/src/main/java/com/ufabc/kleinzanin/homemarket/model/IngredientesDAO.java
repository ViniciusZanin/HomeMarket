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
 * Created by Felippe on 29/04/2015.
 */
public class IngredientesDAO extends SQLiteOpenHelper{
    private static IngredientesDAO dao;
    private List<Ingredientes> ingredientes;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = IngredientesDAO.class.getSimpleName();

    public IngredientesDAO(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }



    public static IngredientesDAO newInstance(Context c) {
        if (dao == null) {
            dao = new IngredientesDAO(c);
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
        try {
            db.execSQL(queryStr1);
            db.execSQL(queryStr2);
            db.execSQL(queryStr3);
            db.execSQL(queryStr4);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: here we must implement a proper way to migrate the schema, version by version
        // currently, it only recreates the database
        String queryStr = context.getString(R.string.drop_table_produtos_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    private void init() {ingredientes = new ArrayList<>();}

    public boolean add(Ingredientes i) {
        String queryStr = context.getString(R.string.insert_ingredientes_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, i.getNome());
            statement.bindString(2, i.getUnidade());
            statement.bindLong(3, i.getQuantidade());
            statement.bindLong(4, i.getReceitaID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add products in the database", e);
            status = false;
        }

        return status;
    }

    public boolean remove(int position) {
        String queryStr = context.getString(R.string.remove_ingredientes_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,position);
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove products in the database", e);
            status = false;
        }

        return status;
    }

    public int size() {
        String queryStr = context.getString(R.string.count_ingredientes_query);
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

    public Ingredientes getItemAt(int position) {
        String queryStr = context.getString(R.string.get_product_query);
        Ingredientes ingrediente = new Ingredientes();


        try {
            Cursor cursor = db.rawQuery(queryStr + position, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                ingrediente.setID(cursor.getInt(0));
                ingrediente.setNome(cursor.getString(1));
                ingrediente.setUnidade(cursor.getString(2));
                ingrediente.setQuantidade(cursor.getInt(3));
                ingrediente.setReceitaID(cursor.getInt(4));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get product in the database", e);
        }

        return ingrediente;
    }

    public ArrayList<Ingredientes> list() {
        ArrayList<Ingredientes> ingredientes = new ArrayList<>();
        String queryStr = context.getString(R.string.list_ingredientes_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Ingredientes ingrediente = new Ingredientes();
                ingrediente.setID(cursor.getInt(0));
                ingrediente.setNome(cursor.getString(1));
                ingrediente.setUnidade(cursor.getString(2));
                ingrediente.setQuantidade(cursor.getInt(3));
                ingrediente.setReceitaID(cursor.getInt(4));
                ingredientes.add(ingrediente);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list ingredientes from the database", e);
        }

        return ingredientes;
    }



}