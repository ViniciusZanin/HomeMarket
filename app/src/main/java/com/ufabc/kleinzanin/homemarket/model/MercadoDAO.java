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
 * Created by Felippe on 17/04/2015.
 */
public class MercadoDAO extends SQLiteOpenHelper {
    private static MercadoDAO dao;
    private List<Mercados> mercados;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = MercadoDAO.class.getSimpleName();

    public MercadoDAO(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    public static MercadoDAO newInstance(Context c) {
        if (dao == null) {
            dao = new MercadoDAO(c);
            dao.init();
        }

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryStr = context.getString(R.string.create_table_mercados_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: here we must implement a proper way to migrate the schema, version by version
        // currently, it only recreates the database
        String queryStr = context.getString(R.string.drop_table_mercados_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the database", e);
        }
    }

    private void init(){
        mercados = new ArrayList<>();
        //loadTestData();
    }

   /* private void loadTestData() {
            Mercados m;

            m = new Mercados();

            m.setNome("Carre4");
            m.setEmail("carre4@carre4.com");
            m.setEndereco("Rua Teste");
            m.setTelefonee("123445");

        mercados.add(m);

    }*/


    //Mercados Order in the Database
    //nome TEXT NOT NULL, email TEXT NOT NULL, telefone TEXT NOT NULL, receitas TEXT NOT NULL
    public boolean add(Mercados mercado){
        String queryStr = context.getString(R.string.insert_mercados_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindString(1, mercado.getNome());
            statement.bindString(2, mercado.getEmail());
            statement.bindString(3, mercado.getTelefone());
            statement.bindString(4, mercado.getEndereco());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add mercado in the database", e);
            status = false;
        }

        return status;
    }

    public boolean edit(Mercados mercado){
        String queryStr = context.getString(R.string.edit_mercados_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindString(1, mercado.getNome());
            statement.bindString(2, mercado.getEmail());
            statement.bindString(3, mercado.getTelefone());
            statement.bindString(4, mercado.getEndereco());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to edit mercado in the database", e);
            status = false;
        }

        return status;
    }

    public boolean remove(int position){
        String queryStr = context.getString(R.string.remove_mercados_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,position);
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove mercado from the database", e);
            status = false;
        }

        return status;
    }

    public int size(){
        String queryStr = context.getString(R.string.count_mercados_query);
        int count = -1;

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count mercados in the database", e);
        }

        return count;
    }

    public Mercados getItemAt(int position){
        String queryStr = context.getString(R.string.get_mercados_query);
        Mercados mercado = new Mercados();

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindLong(1, position);

            Cursor cursor = db.rawQuery(statement.simpleQueryForString(), new String[]{});
            cursor.moveToFirst();
            mercado.setNome(cursor.getString(0));
            mercado.setEmail(cursor.getString(1));
            mercado.setTelefone(cursor.getString(2));
            mercado.setEndereco(cursor.getString(3));
            cursor.close();

        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get mercado from the database", e);
        }

        return mercado;
    }

    public ArrayList<Mercados> list() {
        ArrayList<Mercados> mercados = new ArrayList<>();
        String queryStr = context.getString(R.string.list_mercados_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Mercados mercado = new Mercados();
                mercado.setNome(cursor.getString(0));
                mercado.setEmail(cursor.getString(1));
                mercado.setTelefone(cursor.getString(2));
                mercado.setEndereco(cursor.getString(3));
                mercados.add(mercado);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list mercado from the database", e);
        }

        return mercados;
    }


}
