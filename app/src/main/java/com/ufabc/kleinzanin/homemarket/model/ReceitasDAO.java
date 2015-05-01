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
public class ReceitasDAO extends SQLiteOpenHelper {
    private static ReceitasDAO dao;
    private List<Receitas> receitas;
    private Context context;
    private SQLiteDatabase db;

    private static final String DB_NAME = "HomeMarket.db";
    private static final int DB_VERSION = 1;
    private static final String LOGTAG = ReceitasDAO.class.getSimpleName();

    public ReceitasDAO(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    private void init(){
        receitas = new ArrayList<>();
    }

    public static ReceitasDAO newInstance(Context c) {
        if(dao == null) {
            dao = new ReceitasDAO(c);
            dao.init();
        }
        return dao;

    }

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
        String queryStr = context.getString(R.string.drop_table_receitas_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    //Receitas database order
    //title TEXT NOT NULL, mododefazer TEXT NOT NULL
    public boolean add(Receitas receita){
        String queryStr = context.getString(R.string.insert_receitas_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindString(1, receita.getReceita());
            statement.bindString(2, receita.getModopreparo());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add receita in the database", e);
            status = false;
        }

        return status;
    }

    public boolean edit(Receitas receita){
        String queryStr = context.getString(R.string.edit_receitas_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindString(1, receita.getReceita());
            statement.bindString(2, receita.getModopreparo());
            statement.bindLong(3, receita.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to edit receita in the database", e);
            status = false;
        }

        return status;
    }

    public boolean remove(int position){
        ArrayList<Receitas> receitas;
        dao = ReceitasDAO.newInstance(context);
        receitas = dao.list();
        Receitas receita = receitas.get(position);
        String queryStr = context.getString(R.string.remove_receitas_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindLong(1,receita.getID());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to remove receita from the database", e);
            status = false;
        }

        return status;
    }

    public int size(){
        String queryStr = context.getString(R.string.count_receitas_query);
        int count = -1;

        try {
            Cursor cursor = db.rawQuery(queryStr, null);

            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count receitas in the database", e);
        }

        return count;
    }

    public Receitas getItemAt(int position){
        String queryStr = context.getString(R.string.get_product_query);
        Receitas r = new Receitas();

        try {
            Cursor cursor = db.rawQuery(queryStr + position, null);
            cursor.moveToFirst();
            r.setID(cursor.getInt(0));
            r.setReceita(cursor.getString(1));
            r.setModopreparo(cursor.getString(2));
            cursor.close();

        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get receita from the database", e);
        }

        return r;
    }

    public ArrayList<Receitas> list() {
        ArrayList<Receitas> receitas = new ArrayList<>();
        String queryStr = context.getString(R.string.list_receitas_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Receitas r = new Receitas();
                r.setID(cursor.getInt(0));
                r.setReceita(cursor.getString(1));
                r.setModopreparo(cursor.getString(2));
                receitas.add(r);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list receitas from the database", e);
        }

        return receitas;
    }

    public ArrayList<Receitas> receitas_disp(){
        ArrayList<Receitas> receitas = new ArrayList<>();
        String queryStr = context.getString(R.string.list_receitas_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                boolean disp=false;
                Receitas r = new Receitas();
                r.setID(cursor.getInt(0));
                r.setReceita(cursor.getString(1));
                r.setModopreparo(cursor.getString(2));

                ArrayList<Produtos> produtos;
                ProdutosDao dao_prod = ProdutosDao.newInstance(context);
                produtos = dao_prod.despensa();
                ArrayList<Ingredientes> ingredientes;
                IngredientesDAO dao_ing = IngredientesDAO.newInstance(context);
                ingredientes = dao_ing.receita_igredientes(cursor.getInt(0));

                for(int i = 0; i<ingredientes.size(); i++){
                    Ingredientes ingrediente = ingredientes.get(i);
                    String ing_uni = ingrediente.getUnidade();
                    double ing_quant = ingrediente.getQuantidade();
                    for (int j = 0; j<produtos.size(); j++){
                        Produtos produto = produtos.get(j);
                        if (ingrediente.getNome().equalsIgnoreCase(produto.getNome())) {
                            if (ing_uni.equalsIgnoreCase(produto.getUnidade())) {
                                if (ing_quant <= produto.getQuantidade()) {
                                    disp = true;
                                } else {
                                    disp = false;
                                    break;
                                }
                            } else if (ing_uni.equalsIgnoreCase("gramas") && produto.getUnidade().equalsIgnoreCase("kilogramas")) {
                                if (ing_quant / 1000 <= produto.getQuantidade()) {
                                    disp = true;
                                } else {
                                    disp = false;
                                    break;
                                }
                            } else if (ing_uni.equalsIgnoreCase("kilogramas") && produto.getUnidade().equalsIgnoreCase("gramas")) {
                                if (ing_quant * 1000 <= produto.getQuantidade()) {
                                    disp = true;
                                } else {
                                    disp = false;
                                    break;
                                }
                            } else if (ing_uni.equalsIgnoreCase("mililitos") && produto.getUnidade().equalsIgnoreCase("litros")) {
                                if (ing_quant / 1000 <= produto.getQuantidade()) {
                                    disp = true;
                                } else {
                                    disp = false;
                                    break;
                                }
                            } else if (ing_uni.equalsIgnoreCase("litros") && produto.getUnidade().equalsIgnoreCase("mililitros")) {
                                if (ing_quant * 1000 <= produto.getQuantidade()) {
                                    disp = true;
                                } else {
                                    disp = false;
                                    break;
                                }
                            } else {
                                disp = false;
                                break;
                            }
                        }
                    }
                    if(!disp){
                        break;
                    }
                }

                if(disp){
                    receitas.add(r);
                }
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list receitas from the database", e);
        }
        return receitas;
    }

}
