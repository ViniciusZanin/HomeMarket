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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.adapter.DespensaAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.DespensaAdapterAdd;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DespensaDetail extends ActionBarActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despensa_detail);
        setupProdutoList();

    }

    private void setupProdutoList(){
        listView = (ListView )findViewById(R.id.despensa_add);
        final DespensaDetail self = this;
        listView.setAdapter(new DespensaAdapterAdd(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Produtos> produtos;
                final ProdutosDao dao = ProdutosDao.newInstance(DespensaDetail.this);
                produtos = dao.despensa_prod();
                final Produtos produto = produtos.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(DespensaDetail.this);
                LayoutInflater inflater = DespensaDetail.this.getLayoutInflater();
                View promptsView = inflater.inflate(R.layout.dialog_despensa, null);
                builder.setView(promptsView);
                builder.setPositiveButton(R.string.prod_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog ) dialog;

                        EditText quantidade = (EditText)f.findViewById(R.id.produto_quantidade);
                        int quant = Integer.parseInt(quantidade.getText().toString());

                        if(quant != 0 || String.valueOf(quant) != null){
                            produto.setQuantidade(quant+produto.getQuantidade());
                            dao.edit(produto,produto.getID());
                            startActivity(new Intent(DespensaDetail.this,Despensa.class));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_despensa_detail, menu);
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
