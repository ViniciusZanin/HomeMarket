package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.ufabc.kleinzanin.homemarket.adapter.DespensaAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.ProdutoAdapter;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;
import java.util.List;


public class Despensa extends ActionBarActivity {
    private ProdutosDao dao;
    private ListView listView;
    private Button add;
    private Button remove;
    private Spinner spinner;

    private DespensaDetailFragment detailFragment;
    private DespensaListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despensa);
        init();
        setupProdutoList();
    }

    private void init(){
        this.dao = ProdutosDao.newInstance(this);

        listFragment = (DespensaListFragment )getFragmentManager().findFragmentById(R.id.fragment_list);
        detailFragment = (DespensaDetailFragment ) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if(listFragment != null){
            listView = (ListView )listFragment.getView().findViewById(R.id.list_despensa);
        }


    }

    private void setupProdutoList(){
        listView = (ListView )findViewById(R.id.list_despensa);
        final Despensa self = this;
        listView.setAdapter(new DespensaAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_despensa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_despensa_add){
            startActivity(new Intent(this,DespensaDetail.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
