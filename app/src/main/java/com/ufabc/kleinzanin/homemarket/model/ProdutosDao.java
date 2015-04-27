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

public class ProdutosDAO extends SQLiteOpenHelper {
    private static ProdutosDAO dao;
    private List<Produtos> produtos;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = ProdutosDAO.class.getSimpleName();



    public ProdutosDAO(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    public static ProdutosDAO newInstance(Context c) {
        if (dao == null) {
            dao = new ProdutosDAO(c);
            dao.init();
        }

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryStr = context.getString(R.string.create_table_produtos_query);

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
        String queryStr = context.getString(R.string.drop_table_produtos_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    private void init() {
        produtos = new ArrayList<>();
        // TODO: remove when "insert" operation is implemented in the app
        //loadTestData();
    }

    /*private void loadTestData() {
        Produtos c;

        c = new Produtos();

        c.setNome("Manga");
        c.setPreço("4.99");
        c.setQuantidade(200);
        c.setConsumo(1000);
        c.setChecked(true);
        c.setImagem(R.drawable.manga);
        c.setUnidade("g");
        produtos.add(c);

    }*/


    //ADD ORDER
    //title TEXT NOT NULL, unity TEXT NOT NULL, quantNow INTEGER NOT NULL, quantMonth INTEGER, monthly INTEGER NOT NULL, value TEXT NOT NULL, image BLOB
    public boolean add(Produtos p) {
        String queryStr = context.getString(R.string.insert_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, p.getNome());
            statement.bindString(2, p.getUnidade());
            statement.bindLong(3, p.getQuantidade());
            statement.bindLong(4, p.getConsumo());
            statement.bindLong(5, (p.getChecked()) ? 1:0);
            statement.bindString(6, p.getPreço());
           //statement.bindBlob(7, p.getImagem()); RESOLVER IMAGEM COMO BYTE ARRAY OU SALVAR NA MEMORIA INTERNA.
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add products in the database", e);
            status = false;
        }

        return status;
    }
    public boolean edit(Produtos p){
        String queryStr = context.getString(R.string.edit_produtos_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, p.getNome());
            statement.bindString(2, p.getUnidade());
            statement.bindLong(3, p.getQuantidade());
            statement.bindLong(4, p.getConsumo());
            statement.bindLong(5, (p.getChecked()) ? 1:0);
            statement.bindString(6, p.getPreço());
            //statement.bindBlob(7, p.getImagem()); RESOLVER IMAGEM COMO BYTE ARRAY OU SALVAR NA MEMORIA INTERNA.

            statement.bindLong(8,p.getID()); //ID of the product to change.

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
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1, position);

            Cursor cursor = db.rawQuery(statement.simpleQueryForString(), new String[]{});
            cursor.moveToFirst();

            produto.setID(cursor.getInt(0));
            produto.setNome(cursor.getString(1));
            produto.setUnidade(cursor.getString(2));
            produto.setQuantidade(cursor.getInt(3));
            produto.setConsumo(cursor.getInt(4));
            produto.setChecked((cursor.getInt(5) == 0 ? false:true));
            produto.setPreço(cursor.getString(6));
            //produto.setImagem(cursor.getBlob(7));
            cursor.close();

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
                produto.setQuantidade(cursor.getInt(3));
                produto.setConsumo(cursor.getInt(4));
                produto.setChecked((cursor.getInt(5) == 0 ? false:true));
                produto.setPreço(cursor.getString(6));
                //produto.setImagem(cursor.getBlob(7));
                produtos.add(produto);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list produtos from the database", e);
        }

        return produtos;
    }


}