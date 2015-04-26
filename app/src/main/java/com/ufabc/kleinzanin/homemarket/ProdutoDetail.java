package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDAO;


public class ProdutoDetail extends ActionBarActivity {
    private DespensaDetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_show);
        detail = (DespensaDetailFragment ) getFragmentManager().findFragmentById(R.id.fragment_detail);
        showProduto();
    }

    private void showProduto() {
        ProdutosDAO dao = ProdutosDAO.newInstance();
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Produtos produto = dao.getItemAt(pos);
        detail.showProdutos(produto);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produto_show, menu);
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
        if(id == R.id.action_produto_edit){
            int pos = getIntent().getExtras().getInt("produtoPosition");
            Intent intent = new Intent(this, ProdutoEdit.class);
            intent.putExtra("produtoPosition", pos);
            startActivity(intent);
        }
        if(id == R.id.action_produto_remove){
            ProdutosDAO dao = ProdutosDAO.newInstance();
            int pos = getIntent().getExtras().getInt("produtoPosition");
            dao.remove(pos);
            Intent intent = new Intent(this, Despensa.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
