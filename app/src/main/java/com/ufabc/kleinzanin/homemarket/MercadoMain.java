package com.ufabc.kleinzanin.homemarket;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.ufabc.kleinzanin.homemarket.adapter.MercadoAdapter;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoMain extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private MercadoDAO dao;
    private ListView listView;
    private SearchManager searchManager;
    private SearchView searchView;
    private MercadoListFragment listFragment;
    private MercadoDetailFragment detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_main);
        init();
        setupMercadoList();
    }

    private void init() {
        this.dao = MercadoDAO.newInstance(this);

        listFragment = (MercadoListFragment )getFragmentManager().findFragmentById(R.id.mercadofragment_list);

        if(listFragment != null){
            listView = (ListView ) listFragment.getView().findViewById(R.id.list_mercados);
        }

    }

    private void setupMercadoList() {
        listView = (ListView)findViewById(R.id.list_mercados);
        final MercadoMain self = this;

        listView.setAdapter(new MercadoAdapter(this));
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (detailFragment == null) { // small screen
                    Intent intent = null;

                    intent = new Intent(parent.getContext(), MercadoDetails.class);
                    intent.putExtra("mercadoPosition", (int)id);
                    startActivity(intent);
                } else { // large screen
                    Mercados mercado = dao.getItemAt((int)id);

                    detailFragment.showMercado(mercado);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_main, menu);

        searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.mercado_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

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
        else if(id == R.id.mercado_add){
            startActivity((new Intent(this, MercadoInsert.class)));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listView.setFilterText(newText);
        return true;
    }
}
