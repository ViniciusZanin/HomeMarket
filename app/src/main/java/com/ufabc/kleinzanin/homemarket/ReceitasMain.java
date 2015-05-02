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

import com.ufabc.kleinzanin.homemarket.adapter.ReceitasAdapter;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;


public class ReceitasMain extends ActionBarActivity implements SearchView.OnQueryTextListener{
    private ReceitasDAO dao;
    private ListView listView;
    private SearchView searchView;
    private SearchManager searchManager;
    private ReceitasListFragment listFragment;
    private ReceitasDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        init();
        setupReceitasList();
    }

    private void init() {
        this.dao = ReceitasDAO.newInstance(this);

        listFragment = (ReceitasListFragment )getFragmentManager().findFragmentById(R.id.receitalist_fragment);
        detailFragment = (ReceitasDetailFragment )getFragmentManager().findFragmentById(R.id.receita_fragmentdetails);
        if(listFragment != null){
            listView = (ListView )listFragment.getView().findViewById(R.id.receitas_list);
        }
    }

    private void setupReceitasList(){
        listView = (ListView )findViewById(R.id.receitas_list);
        final ReceitasMain self = this;
        listView.setAdapter(new ReceitasAdapter(this));
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (detailFragment == null) {
                    Intent intent = null;

                    intent = new Intent(parent.getContext(), ReceitasDetails.class);
                    intent.putExtra("receitaPosition", (int)id-1);
                    startActivity(intent);
                } else {
                    Receitas receitas = dao.getItemAt(position);

                    detailFragment.showReceitas(receitas, 0);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receitas, menu);

        searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.receita_search).getActionView();
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
        else if(id == R.id.receita_add){
            startActivity((new Intent(this, ReceitasInsert.class)));
        }
        else if(id == R.id.receita_search){
            return true;
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
