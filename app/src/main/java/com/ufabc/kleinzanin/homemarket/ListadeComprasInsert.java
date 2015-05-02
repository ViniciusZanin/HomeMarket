package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.adapter.ListaDeCompraAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.ListaDeComprasInsertAdapter;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;


public class ListadeComprasInsert extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_compras_insert);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listade_compras_insert, menu);
        init();
        return true;
    }

    private void init() {
        listView = (ListView )findViewById(R.id.list_listacompra_insert);
        listView.setAdapter(new ListaDeComprasInsertAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Produtos> produtos;
                final ProdutosDao dao = ProdutosDao.newInstance(ListadeComprasInsert.this);
                produtos = dao.lista_compras_insert();
                final Produtos produto = produtos.get(position);
                final ListaComprasDAO dao2 = ListaComprasDAO.newInstance(ListadeComprasInsert.this);
                final ListaCompras listadeCompras = dao2.getMaxID();
                final ListaComprasProdutosDAO dao3 = ListaComprasProdutosDAO.newInstance(ListadeComprasInsert.this);
                final ArrayList<ListaComprasProdutos> listaComprasProdutos = dao3.listIDrecipe(listadeCompras.getID());
                AlertDialog.Builder builder = new AlertDialog.Builder(ListadeComprasInsert.this);
                LayoutInflater inflater = ListadeComprasInsert.this.getLayoutInflater();
                View promptsView = inflater.inflate(R.layout.dialog_listacomprainsert, null);
                builder.setView(promptsView);
                builder.setPositiveButton(R.string.prod_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Boolean error = false;
                        Boolean error2 = false;
                        Dialog f = (Dialog) dialog;
                        EditText quantidade = (EditText) f.findViewById(R.id.produto_quantidade);
                        if (quantidade.getText().toString().equalsIgnoreCase("") || Double.parseDouble(quantidade.getText().toString()) == 0) {
                            error2 = true;
                            quantidade.setError("Não é possivel colocar 0 na quantidade");
                        }
                        if (!error2) {
                            double quant = Double.parseDouble(quantidade.getText().toString());
                            Double preco = produto.getPreço() * quant;
                            ListaComprasProdutos newprod = new ListaComprasProdutos();
                            newprod.setNome(produto.getNome());
                            newprod.setQuantidade(quant);
                            newprod.setUnidade(produto.getUnidade());
                            newprod.setPreco(preco);
                            newprod.setLista_id(listadeCompras.getID());
                            for (int i = 0; i < listaComprasProdutos.size(); i++) {
                                ListaComprasProdutos prod = listaComprasProdutos.get(i);
                                if (newprod.getNome().equalsIgnoreCase(prod.getNome())) {
                                    prod.setQuantidade(newprod.getQuantidade());
                                    prod.setPreco(newprod.getPreco());
                                    dao3.edit(prod);
                                    error = true;
                                }
                            }
                            if (!error) {
                                dao3.add(newprod);
                                double precol = listadeCompras.getPreco() + preco;
                                listadeCompras.setPreco(precol);
                                dao2.editpreco(listadeCompras);
                                startActivity(new Intent(getApplicationContext(), ListadeCompras.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Produto atualiado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListadeCompras.class));
                            }
                        }
                    }
                });
                builder.setNegativeButton(R.string.prodcancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
