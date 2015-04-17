package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.model.HomeMarketDao;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;


public class ReceitasMain extends ActionBarActivity {
    private ReceitasDAO dao;
    private ListView listView;

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
        this.dao = ReceitasDAO.newInstance();

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (detailFragment == null) {
                    Intent intent = null;

                    intent = new Intent(parent.getContext(), ReceitasDetails.class);
                    intent.putExtra("receitaPosition", position);
                    startActivity(intent);
                } else {
                    Receitas receitas = dao.getItemAt(position);

                    detailFragment.showReceitas(receitas);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receitas, menu);
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
}
