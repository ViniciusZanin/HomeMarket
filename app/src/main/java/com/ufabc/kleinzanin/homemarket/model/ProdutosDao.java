package com.ufabc.kleinzanin.homemarket.model;

/**
 * Created by Vinicius on 16/04/2015.
 */
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

public class ProdutosDao extends SQLiteOpenHelper {
    private static ProdutosDao dao;
    private List<Produtos> produtos;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = ProdutosDao.class.getSimpleName();



    public ProdutosDao(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    public static ProdutosDao newInstance(Context c) {
        if (dao == null) {
            dao = new ProdutosDao(c);
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

    private void init() {
        produtos = new ArrayList<>();
    }

    //ADD ORDER
    //title TEXT NOT NULL, unity TEXT NOT NULL, quantNow INTEGER NOT NULL, quantMonth INTEGER, monthly INTEGER NOT NULL, value TEXT NOT NULL, image BLOB
    public boolean add(Produtos p) {
        String queryStr = context.getString(R.string.insert_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, p.getNome());
            statement.bindString(2, p.getUnidade());
            statement.bindDouble(3, p.getQuantidade());
            statement.bindDouble(4, p.getConsumo());
            statement.bindLong(5, (p.getChecked()) ? 1:0);
            statement.bindDouble(6, p.getPreço());
           statement.bindString(7, p.getImagem());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add products in the database", e);
            status = false;
        }

        return status;
    }
    public boolean edit(Produtos p, int position){
        String queryStr = context.getString(R.string.edit_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, p.getNome());
            statement.bindString(2, p.getUnidade());
            statement.bindDouble(3, p.getQuantidade());
            statement.bindDouble(4, p.getConsumo());
            statement.bindLong(5, (p.getChecked()) ? 1:0);
            statement.bindDouble(6, p.getPreço());
            statement.bindString(7, p.getImagem());

            statement.bindLong(8, p.getID()); //ID of the product to change.

            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to edit products in the database", e);
            status = false;
        }

        return status;
    }
    public boolean editprodquant(Produtos p, int position){
        String queryStr = context.getString(R.string.edit_produto_quant_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindDouble(1, p.getQuantidade());

            statement.bindLong(2, p.getID()); //ID of the product to change.

            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to edit products in the database", e);
            status = false;
        }

        return status;
    }

    public boolean remove(int position) {
        String queryStr = context.getString(R.string.remove_produtos_query);
        boolean status = true;
        ArrayList<Produtos>produtos;
        dao = ProdutosDao.newInstance(context);
        produtos = dao.list();
        Produtos produto = produtos.get(position);

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,produto.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove products in the database", e);
            status = false;
        }

        return status;
    }

 /*   public Produtos[] list(Produtos produto) {
        return produtos.toArray(new Produtos[produtos.size()]);
    }*/

    public int size() {
        String queryStr = context.getString(R.string.count_produtos_query);
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

    public Produtos getItemAt(int position) {
        String queryStr = context.getString(R.string.get_product_query);
        Produtos produto = new Produtos();


        try {
            Cursor cursor = db.rawQuery(queryStr + position, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                produto.setID(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setUnidade(cursor.getString(2));
                produto.setQuantidade(cursor.getDouble(3));
                produto.setConsumo(cursor.getDouble(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false : true));
                produto.setPreço(cursor.getDouble(6));
                produto.setImagem(cursor.getString(7));
                cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get product in the database", e);
        }

        return produto;
    }

    public ArrayList<Produtos> list() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        String queryStr = context.getString(R.string.list_produtos_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Produtos produto = new Produtos();
                produto.setID(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setUnidade(cursor.getString(2));
                produto.setQuantidade(cursor.getDouble(3));
                produto.setConsumo(cursor.getDouble(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false:true));
                produto.setPreço(cursor.getDouble(6));
                produto.setImagem(cursor.getString(7));
                produtos.add(produto);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list produtos from the database", e);
        }

        return produtos;
    }

    public ArrayList<Produtos> despensa() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        String queryStr = context.getString(R.string.list_despensa_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Produtos produto = new Produtos();
                produto.setID(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setUnidade(cursor.getString(2));
                produto.setQuantidade(cursor.getDouble(3));
                produto.setConsumo(cursor.getDouble(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false:true));
                produto.setPreço(cursor.getDouble(6));
                produto.setImagem(cursor.getString(7));
                produtos.add(produto);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list produtos from the database", e);
        }

        return produtos;
    }

    public ArrayList<Produtos> despensa_prod() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        String queryStr = context.getString(R.string.list_despensaprod_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Produtos produto = new Produtos();
                produto.setID(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setUnidade(cursor.getString(2));
                produto.setQuantidade(cursor.getDouble(3));
                produto.setConsumo(cursor.getDouble(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false:true));
                produto.setPreço(cursor.getDouble(6));
                produto.setImagem(cursor.getString(7));
                produtos.add(produto);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list produtos from the database", e);
        }

        return produtos;
    }
    public ArrayList<Produtos> despensa_porcentagem() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        String queryStr = context.getString(R.string.get_product_checked_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Produtos produto = new Produtos();
                produto.setID(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setUnidade(cursor.getString(2));
                produto.setQuantidade(cursor.getDouble(3));
                produto.setConsumo(cursor.getDouble(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false:true));
                produto.setPreço(cursor.getDouble(6));
                produto.setImagem(cursor.getString(7));
                produtos.add(produto);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list produtos from the database", e);
        }

        return produtos;
    }

    public void dropTable(){
        String queryStr = context.getString(R.string.drop_table_produtos_query);
        db.execSQL(queryStr);
    }


}