package com.ufabc.kleinzanin.homemarket;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.model.HomeMarketDao;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;


public class Despensa extends ActionBarActivity {
    private ProdutosDao dao;
    private ListView listView;

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
        this.dao = ProdutosDao.newInstance();

        listFragment = (DespensaListFragment )getFragmentManager().findFragmentById(R.id.fragment_list);
        detailFragment = (DespensaDetailFragment ) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if(listFragment != null){
            listView = (ListView )listFragment.getView().findViewById(R.id.list_despensa);
        }


    }

    private void setupProdutoList(){
        listView = (ListView )findViewById(R.id.list_despensa);
        final Despensa self = this;
        listView.setAdapter(new ProdutoAdapter(this));
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

        return super.onOptionsItemSelected(item);
    }
}
