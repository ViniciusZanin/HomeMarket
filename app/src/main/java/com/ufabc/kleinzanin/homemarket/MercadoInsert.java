package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoInsert extends ActionBarActivity {

    private MercadoDAO dao = MercadoDAO.newInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_insert);
    }

    private void insert() {
            String nome = ((EditText)findViewById(R.id.insert_name)).getText().toString();
            String email = ((EditText)findViewById(R.id.insert_email)).getText().toString();
            String telefone = ((EditText)findViewById(R.id.insert_telefone)).getText().toString();
            String endereco = ((EditText)findViewById(R.id.insert_endereco)).getText().toString();

            Mercados mercado = new Mercados();

            mercado.setNome(nome);
            mercado.setEmail(email);
            mercado.setTelefone(telefone);
            mercado.setEndereco(endereco);
            dao.add(mercado);

            Toast.makeText(this, getString(R.string.insert_status_ok), Toast.LENGTH_SHORT).show();
            startActivity((new Intent(this, MercadoMain.class)));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_insert, menu);
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
        else if(id == R.id.mercadoinsert_button){
            insert();
        }

        return super.onOptionsItemSelected(item);
    }
}
