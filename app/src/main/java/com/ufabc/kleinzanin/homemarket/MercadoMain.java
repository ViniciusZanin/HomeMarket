package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoMain extends ActionBarActivity {

    private MercadoDAO dao;
    private ListView listView;

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
        this.dao = MercadoDAO.newInstance();

        listFragment = (MercadoListFragment )getFragmentManager().findFragmentById(R.id.mercadofragment_list);

        if(listFragment != null){
            listView = (ListView ) listFragment.getView().findViewById(R.id.list_mercados);
        }

    }

    private void setupMercadoList() {
        listView = (ListView)findViewById(R.id.list_mercados);
        final MercadoMain self = this;

        listView.setAdapter(new MercadoAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (detailFragment == null) { // small screen
                    Intent intent = null;

                    intent = new Intent(parent.getContext(), MercadoDetails.class);
                    intent.putExtra("mercadoPosition", position);
                    startActivity(intent);
                } else { // large screen
                    Mercados mercado = dao.getItemAt(position);

                    detailFragment.showMercado(mercado);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
