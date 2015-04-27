package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoEdit extends ActionBarActivity {

    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_edit);
        ini();
    }

    private void ini() {
        MercadoDAO dao = MercadoDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("mercadoPosition");
        Mercados mercado = dao.getItemAt(pos);
        nome = (EditText ) findViewById(R.id.editmercado_name);
        email = (EditText ) findViewById(R.id.editmercado_email);
        telefone = (EditText ) findViewById(R.id.editmercado_telefone);
        endereco = (EditText ) findViewById(R.id.editmercado_endereco);
        nome.setText(mercado.getNome());
        email.setText(mercado.getEmail());
        telefone.setText(mercado.getTelefone());
        endereco.setText(mercado.getEndereco());
    }

    private void insert() {
        MercadoDAO dao = MercadoDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("mercadoPosition");
        Mercados mercado = dao.getItemAt(pos);
        String nnome = ((EditText)findViewById(R.id.editmercado_name)).getText().toString();
        String nemail = ((EditText)findViewById(R.id.editmercado_email)).getText().toString();
        String ntelefone = ((EditText)findViewById(R.id.editmercado_telefone)).getText().toString();
        String nendereco = ((EditText)findViewById(R.id.editmercado_endereco)).getText().toString();



        mercado.setNome(nnome);
        mercado.setEmail(nemail);
        mercado.setTelefone(ntelefone);
        mercado.setEndereco(nendereco);
        dao.edit(mercado);

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
