package com.ufabc.kleinzanin.homemarket;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.ProdutosDAO;


public class ListadeCompras extends ActionBarActivity {
    private ProdutosDAO dao;
    private TextView listprod;
    private TextView pr;
    private Button enviar;
    String produtos = "";
    double preço = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_compras);
        init();
    }


    private void init() {
        this.dao = ProdutosDAO.newInstance(this);
        for (int i = 0; i < dao.size(); i++) {
            if (dao.getItemAt(i).getChecked()) {
                if (dao.getItemAt(i).getConsumo() < dao.getItemAt(i).getQuantidade()) {
                    produtos = produtos + "/n" + Integer.toString(dao.getItemAt(i).getConsumo() - dao.getItemAt(i).getQuantidade()) + "x " + dao.getItemAt(i).getNome();
                    preço = preço + (Double.parseDouble(dao.getItemAt(i).getPreço()))*(dao.getItemAt(i).getConsumo() - dao.getItemAt(i).getQuantidade());
                }
            }
        }
        listprod = ((TextView )findViewById(R.id.list_listacompra));
        listprod.setText("Manga x 800");
        pr = ((TextView )findViewById(R.id.preço_lista));
        pr.setText("R$3,99");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listade_compras, menu);
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
