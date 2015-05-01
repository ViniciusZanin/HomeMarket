package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.io.File;
import java.util.ArrayList;


public class Configuracao extends ActionBarActivity {
    private TextView limpar_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        init();
    }

    private void init() {
        limpar_app = (TextView )findViewById(R.id.limp_app);
        final Intent intent = new Intent(this, ReceitasMain.class);
        limpar_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracao.this);
                builder.setMessage("Deseja limpar a aplicação?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Produtos> produtos;
                        ProdutosDao dao_prod = ProdutosDao.newInstance(Configuracao.this);
                        produtos = dao_prod.list();
                        for(int i = 0; i<produtos.size(); i++){
                            Produtos produto = produtos.get(i);
                            if(produto.getImagem() != null){
                                File file = new File(produto.getImagem());
                                file.delete();
                            }
                        }
                        dao_prod.dropTable();
                        IngredientesDAO dao_ing = IngredientesDAO.newInstance(Configuracao.this);
                        dao_ing.dropTable();
                        MercadoDAO dao_merc = MercadoDAO.newInstance(Configuracao.this);
                        dao_merc.dropTable();
                        ReceitasDAO dao_rec = ReceitasDAO.newInstance(Configuracao.this);
                        dao_rec.dropTable();
                        startActivity(new Intent(Configuracao.this, MenuPrincipal.class));

                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracao, menu);
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
