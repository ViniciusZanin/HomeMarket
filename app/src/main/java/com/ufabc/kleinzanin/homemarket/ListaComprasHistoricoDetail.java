package com.ufabc.kleinzanin.homemarket;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.adapter.ListaDeCompraAdapter;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;

import java.util.ArrayList;


public class ListaComprasHistoricoDetail extends ActionBarActivity {

    private ListaComprasDAO dao;
    private ListaComprasProdutosDAO dao2;
    private String Titulo = "";
    private String preco = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras_historico_detail);
        init();
    }

    private void init() {
        dao = ListaComprasDAO.newInstance(this);

        ArrayList<ListaCompras> listaComprases;
        listaComprases = dao.list();
        int id = getIntent().getExtras().getInt("listaID");
       for(int i = 0; i < listaComprases.size(); i ++){
           ListaCompras listaCompras = listaComprases.get(i);
           if(listaCompras.getID() == id){
               Titulo = "Data da Lista de Compra:" + listaCompras.getData();
               preco = "Preço R$" + String.valueOf(listaCompras.getPreco());
           }
       }
        setTitle(Titulo);
        TextView titulo = (TextView )findViewById(R.id.lista_detail_title);
        TextView preço = (TextView )findViewById(R.id.lista_detail_preco);
        titulo.setText(Titulo);
        preço.setText(preco);
        ListView listView = (ListView )findViewById(R.id.list_detail_compra);
        listView.setAdapter(new ListaDeCompraAdapter(this,id));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_compras_historico_detail, menu);
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
