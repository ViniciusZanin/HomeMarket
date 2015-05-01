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
public class ListaComprasProdutosDAO extends SQLiteOpenHelper{
    private static ListaComprasProdutosDAO dao;
    private List<ListaComprasProdutos> listaComprasProdutoses;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = ListaComprasProdutosDAO.class.getSimpleName();

    public ListaComprasProdutosDAO(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }
    public static ListaComprasProdutosDAO newInstance(Context c) {
        if (dao == null) {
            dao = new ListaComprasProdutosDAO(c);
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
        String queryStr = context.getString(R.string.dropb_table_listacompras_produtos_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    private void init() {
        listaComprasProdutoses = new ArrayList<>();
    }


    public boolean add(ListaComprasProdutos i) {
        String queryStr = context.getString(R.string.insert_listacompras_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, i.getNome());
            statement.bindDouble(2, i.getQuantidade());
            statement.bindDouble(3, i.getPreco());
            statement.bindLong(4, i.getLista_id());
            statement.bindString(5, i.getUnidade());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add ingredientes in the database", e);
            status = false;
        }

        return status;
    }

    public boolean edit(ListaComprasProdutos i){
        String queryStr = context.getString(R.string.edit_listacompras_produtos_quant_query);
        boolean status = true;
        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindDouble(1, i.getQuantidade());
            statement.bindDouble(2, i.getPreco());
            statement.bindLong(3, i.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add ingredientes in the database", e);
            status = false;
        }
        return status;
    }

    public boolean remove(int position) {
        String queryStr = context.getString(R.string.remove_listacompras_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,position);
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove Lista in the database", e);
            status = false;
        }

        return status;
    }

    public int size() {
        String queryStr = context.getString(R.string.count_listacompras_produtos_query);
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
    public ListaComprasProdutos getItemAt(int position) {
        String queryStr = context.getString(R.string.get_listacompras_produtos_query);
        ListaComprasProdutos listaCompras = new ListaComprasProdutos();


        try {
            Cursor cursor = db.rawQuery(queryStr + position, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                listaCompras.setID(cursor.getInt(0));
                listaCompras.setNome(cursor.getString(1));
                listaCompras.setQuantidade(cursor.getDouble(2));
                listaCompras.setPreco(cursor.getDouble(3));
                listaCompras.setLista_id(cursor.getInt(4));
                listaCompras.setUnidade(cursor.getString(5));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get ingrediente in the database", e);
        }

        return listaCompras;
    }

    public ArrayList<ListaComprasProdutos> list() {
        ArrayList<ListaComprasProdutos> listaCompras = new ArrayList<>();
        String queryStr = context.getString(R.string.list_listacompras_produtos_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                ListaComprasProdutos listaCompra = new ListaComprasProdutos();
                listaCompra.setID(cursor.getInt(0));
                listaCompra.setNome(cursor.getString(1));
                listaCompra.setQuantidade(cursor.getDouble(2));
                listaCompra.setPreco(cursor.getDouble(3));
                listaCompra.setLista_id(cursor.getInt(4));
                listaCompra.setUnidade(cursor.getString(5));
                listaCompras.add(listaCompra);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list ingredientes from the database", e);
        }

        return listaCompras;
    }

    public ArrayList<ListaComprasProdutos> listmaxid() {
        ArrayList<ListaComprasProdutos> listaCompras = new ArrayList<>();
        String queryStr = context.getString(R.string.get_listacompras_produtos_max_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                ListaComprasProdutos listaCompra = new ListaComprasProdutos();
                listaCompra.setID(cursor.getInt(0));
                listaCompra.setNome(cursor.getString(1));
                listaCompra.setQuantidade(cursor.getDouble(2));
                listaCompra.setPreco(cursor.getDouble(3));
                listaCompra.setLista_id(cursor.getInt(4));
                listaCompra.setUnidade(cursor.getString(5));
                listaCompras.add(listaCompra);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list ingredientes from the database", e);
        }

        return listaCompras;
    }

    public ArrayList<ListaComprasProdutos> listIDrecipe(int ID) {
        ArrayList<ListaComprasProdutos> listaCompras = new ArrayList<>();
        String queryStr = "SELECT * FROM listacompras_produtos WHERE lista_id = " + ID;

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                ListaComprasProdutos listaCompra = new ListaComprasProdutos();
                listaCompra.setID(cursor.getInt(0));
                listaCompra.setNome(cursor.getString(1));
                listaCompra.setQuantidade(cursor.getDouble(2));
                listaCompra.setPreco(cursor.getDouble(3));
                listaCompra.setLista_id(cursor.getInt(4));
                listaCompra.setUnidade(cursor.getString(5));
                listaCompras.add(listaCompra);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list ingredientes from the database", e);
        }

        return listaCompras;
    }

}
