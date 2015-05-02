package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.adapter.ListaComprasHistoricoAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.ListaDeComprasInsertAdapter;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;

import java.util.ArrayList;


public class ListaComprasHistorico extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras_historico);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_compras_historico, menu);
        init();
        return true;
    }

    private void init() {
        ListView listView = (ListView )findViewById(R.id.list_historico_compras);
        listView.setAdapter(new ListaComprasHistoricoAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ListaComprasHistoricoDetail.class);
                ListaComprasDAO dao = ListaComprasDAO.newInstance(getApplicationContext());
                ArrayList<ListaCompras> listaComprases = dao.list();
                int i = listaComprases.get(position).getID();
                intent.putExtra("listaID", i);
                startActivity(intent);
            }
        });
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
