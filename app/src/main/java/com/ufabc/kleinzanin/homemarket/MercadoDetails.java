package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoDetails extends ActionBarActivity {

    private MercadoDetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_details);
        detail = (MercadoDetailFragment )getFragmentManager().findFragmentById(R.id.mercadofragment_detail);
        showMercado();

    }

    private void showMercado() {
        MercadoDAO dao = MercadoDAO.newInstance();
        int pos = getIntent().getExtras().getInt("mercadoPosition");
        Mercados mercados = dao.getItemAt(pos);
        detail.showMercado(mercados);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_details, menu);
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
        else if (id == R.id.mercadoedit_button) {
                int pos = getIntent().getExtras().getInt("mercadoPosition");
                Intent intent = new Intent(this, MercadoEdit.class);
                intent.putExtra("mercadoPosition", pos);
                startActivity(intent);
            }
        else if(id == R.id.mercadodelete_button) {
            MercadoDAO dao = MercadoDAO.newInstance();
            int pos = getIntent().getExtras().getInt("mercadoPosition");
            dao.remove(pos);
            Intent intent = new Intent(this, MercadoMain.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }
}
