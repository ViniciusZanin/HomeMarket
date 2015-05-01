package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;


public class ProdutoDetail extends ActionBarActivity {
    private static final String LOGTAG = ProdutoDetail.class.getSimpleName() ;
    private ProdutoDetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_show);
        detail = (ProdutoDetailFragment) getFragmentManager().findFragmentById(R.id.fragment_detail);
        showProduto();
    }

    private void showProduto() {
        ArrayList<Produtos>produtos;
        ProdutosDao dao = ProdutosDao.newInstance(this);
        produtos = dao.list();
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Log.e(LOGTAG, String.valueOf(pos));
        Produtos produto = produtos.get(pos);
        setTitle(produto.getNome());
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
        if(id == R.id.action_produto_remove) {
            ProdutosDao dao = ProdutosDao.newInstance(this);
            ListaComprasDAO dao2 = ListaComprasDAO.newInstance(this);
            ListaComprasProdutosDAO dao3 = ListaComprasProdutosDAO.newInstance(this);
            ListaCompras listaCompras = dao2.getMaxID();
            ArrayList<ListaComprasProdutos> listaComprasProdutoses = dao3.listIDrecipe(listaCompras.getID());
            int pos = getIntent().getExtras().getInt("produtoPosition");
            ArrayList<Produtos> produtos;
            produtos = dao.list();
            Produtos produto = produtos.get(pos);
            for (int i = 0; i < listaComprasProdutoses.size(); i++) {
                ListaComprasProdutos lprod = listaComprasProdutoses.get(i);
                if (produto.getNome().equalsIgnoreCase(lprod.getNome())) {
                    dao3.remove(lprod.getID());
                }
            }
            dao.remove(pos);
            Intent intent = new Intent(this, Produto.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
