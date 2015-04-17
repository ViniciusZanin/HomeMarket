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
 * Created by Lucas on 05/04/2015.
 */
public class HomeMarketDao extends SQLiteOpenHelper {
    private static HomeMarketDao dao;
    private Context context;
    private SQLiteDatabase db;
    private List<Produtos> produtos;

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 1;

    private static final String LOGTAG = HomeMarketDao.class.getName();

    protected HomeMarketDao(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    public static HomeMarketDao newInstance(Context c) {
        if (dao == null) {
            dao = new HomeMarketDao(c);
        } else
            dao.context = c;

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryStr = context.getString(R.string.create_table_query);

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
        String queryStr = context.getString(R.string.drop_table_query);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop the databse", e);
        }
    }

    public int size() {
        String queryStr = context.getString(R.string.count_tasks_query);
        int count = -1;

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count tasks in the database", e);
        }

        return count;
    }

    public boolean add(Produtos produto) {
        String queryStr = context.getString(R.string.insert_task_query);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

           //statement.bindString(1, homeMarket.getTitle());
           // statement.bindLong(2, (homeMarket.isDone()) ? 1 : 0);
           // statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count tasks in the database", e);
            status = false;
        }

        return status;
    }

    public boolean checkTaskById(long id) {
        // TODO: update the db to check a task

        return false;
    }

    public boolean uncheckTaskById(long id) {
        // TODO: update the db to uncheck a task

        return false;
    }

    public Produtos getTaskById(int id) {
        // TODO: select only one element in the task table

        return null;
    }

    public boolean removeProduto(Produtos produto) {
        // TODO: remove a task from the db

        return false;
    }



    public ArrayList<Produtos> list() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        String queryStr = context.getString(R.string.list_tasks_query);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Produtos produto = new Produtos();

            //    homeMarket.setId(cursor.getLong(0));
            //    homeMarket.setTitle(cursor.getString(1));
            //   homeMarket.setDone((cursor.getLong(2) == 0) ? false : true);
            //    produtos.add(homeMarket);
            //    cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list tasks from the database", e);
        }

        return produtos;
    }

}