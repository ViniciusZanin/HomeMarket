package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDAO;


public class ProdutoInsert extends ActionBarActivity {

    private ProdutosDAO dao = ProdutosDAO.newInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_insert);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produto_insert, menu);
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
        if(id == R.id.action_produto_insert){
            insert();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        String nome = ((EditText ) findViewById(R.id.insert_produto_nome)).getText().toString();
        String quantidade = ((EditText )findViewById(R.id.insert_produto_quantidade)).getText().toString();
        String preço = ((EditText )findViewById(R.id.insert_produto_preço)).getText().toString();
        String consumo = ((EditText )findViewById(R.id.insert_produto_consumo)).getText().toString();
        String unidade = ((EditText)findViewById(R.id.insert_unidade)).getText().toString();
        boolean check = ((CheckBox )findViewById(R.id.insert_produto_check)).isChecked();
        Produtos produto = new Produtos();

        produto.setNome(nome);
        produto.setQuantidade(Integer.parseInt(quantidade));
        produto.setPreço(preço);
        if(check){
            produto.setConsumo(Integer.parseInt(consumo));}
        produto.setChecked(check);
        produto.setUnidade(unidade);
        dao.add(produto);
        Toast.makeText(this,"Produto Adicionado",Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,Despensa.class)));

    }
}
