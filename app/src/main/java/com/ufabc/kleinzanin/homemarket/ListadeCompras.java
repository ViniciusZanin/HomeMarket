package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.adapter.ListaDeCompraAdapter;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ListadeCompras extends ActionBarActivity {
    private static final String LOGTAG = ListadeCompras.class.getSimpleName();
    private ProdutosDao dao;
    private ListaComprasDAO dao2;
    private ListaComprasProdutosDAO dao3;
    private ListView listprod;
    private TextView pr;
    private Button enviar;
    private Button comprada;
    String produtos = "";
    double preço = 0;
    Boolean okmail = false;
    String selectm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_compras);
        init();
    }


    private void init() {
        this.dao = ProdutosDao.newInstance(this);
        this.dao2 = ListaComprasDAO.newInstance(this);
        this.dao3 = ListaComprasProdutosDAO.newInstance(this);
        final ListadeCompras self = this;
        ListaCompras listaCompras = dao2.getMaxID();
        final String date;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        date = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR));
        if(listaCompras.getID() == 0){
            ListaCompras firstLista = new ListaCompras();
            firstLista.setData("0");
            firstLista.setPreco(0);
            dao2.add(firstLista);
            listaCompras = dao2.getMaxID();
        }
        ArrayList<Produtos> produtos;
        produtos = dao.lista_compras_prod_missing();
        Log.e(LOGTAG,String.valueOf(listaCompras.getID()) + " " + listaCompras.getData());
        ArrayList<ListaComprasProdutos> testList = dao3.list();
        if(testList.size() == 0){
            for(int i = 0; i < produtos.size();i++ ){
                Produtos produto = produtos.get(i);
                ListaComprasProdutos newlprod = new ListaComprasProdutos();
                newlprod.setNome(produto.getNome());
                newlprod.setQuantidade(produto.getConsumo()-produto.getQuantidade());
                newlprod.setPreco((produto.getConsumo()-produto.getQuantidade())*produto.getPreço());
                Log.e(LOGTAG, produto.getUnidade());
                newlprod.setUnidade(produto.getUnidade());
                Log.e(LOGTAG, newlprod.getUnidade());
                newlprod.setLista_id(listaCompras.getID());
                dao3.add(newlprod);
            }
        }

        final ArrayList<ListaComprasProdutos> listaComprasProdutoses = dao3.listIDrecipe(listaCompras.getID());


        for(int i = 0; i < produtos.size(); i++){
            Produtos produto = produtos.get(i);
            boolean equal = false;
            for(int j = 0; j < listaComprasProdutoses.size(); j ++){
                ListaComprasProdutos lprod = listaComprasProdutoses.get(j);
                Log.e(LOGTAG,lprod.getNome()+" " + String.valueOf(lprod.getLista_id()) + String.valueOf(lprod.getID()));
                if(produto.getNome().equalsIgnoreCase(lprod.getNome()) && ((produto.getConsumo()-produto.getQuantidade()) != lprod.getQuantidade())){
                    lprod.setQuantidade(produto.getConsumo()-produto.getQuantidade());
                    lprod.setPreco((produto.getConsumo()-produto.getQuantidade())*produto.getPreço());
                    dao3.edit(lprod);
                    equal = true;
                } else if(produto.getNome().equalsIgnoreCase(lprod.getNome()) && ((produto.getConsumo()-produto.getQuantidade()*produto.getPreço()) != lprod.getPreco())){
                    lprod.setQuantidade(produto.getConsumo()-produto.getQuantidade());
                    lprod.setPreco((produto.getConsumo()-produto.getQuantidade())*produto.getPreço());
                    dao3.edit(lprod);
                    equal = true;
                } else if(produto.getNome().equalsIgnoreCase(lprod.getNome())){
                    equal = true;
                }
            }
            Log.e(LOGTAG,String.valueOf(equal));
           if(equal == false){
                ListaComprasProdutos newlprod = new ListaComprasProdutos();
                newlprod.setNome(produto.getNome());
                newlprod.setQuantidade(produto.getConsumo()-produto.getQuantidade());
                newlprod.setPreco((produto.getConsumo()-produto.getQuantidade())*produto.getPreço());
                newlprod.setUnidade(produto.getUnidade());
                newlprod.setLista_id(listaCompras.getID());
                dao3.add(newlprod);
            }
        }
        for(int i = 0; i < listaComprasProdutoses.size(); i ++){
            ListaComprasProdutos liprod = listaComprasProdutoses.get(i);
            preço = preço + (liprod.getPreco());
        }
        if(listaCompras.getPreco() != preço) {
            listaCompras.setPreco(preço);
            listaCompras.setData(date);
            dao2.editpreco(listaCompras);
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
        ArrayList<ListaComprasProdutos> verify = dao3.listIDrecipe(listaCompras.getID());
        if(verify.size() > 0) {
            listprod = (ListView) findViewById(R.id.list_listacompra);
            listprod.setAdapter(new ListaDeCompraAdapter(getApplicationContext(), listaCompras.getID()));
        }

        DecimalFormat fmt = new DecimalFormat("0.00");
        String srt = fmt.format(listaCompras.getPreco());
        pr = (TextView )findViewById(R.id.preço_lista);
        pr.setText("R$" + srt);

        enviar = (Button )findViewById(R.id.button_send_lista);
        final ListaCompras finalListaCompras = listaCompras;
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MercadoDAO dao4 = MercadoDAO.newInstance(getApplicationContext());
                final ArrayList<Mercados> mercados = dao4.list();
                final String[] mercado = new String[mercados.size()];
                for (int i = 0; i < mercados.size(); i++) {
                    Mercados m = mercados.get(i);
                    mercado[i] = m.getNome();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ListadeCompras.this);
                builder.setTitle("Escolha o mercado");
                builder.setItems(mercado, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < mercados.size(); i++) {
                            Mercados m = mercados.get(i);
                            if (mercado[which].equalsIgnoreCase(m.getNome())) {
                                selectm = m.getEmail();
                            }

                        }
                        String mail = "";
                        for (int i = 0; i < listaComprasProdutoses.size(); i++) {
                            ListaComprasProdutos prod = listaComprasProdutoses.get(i);
                            mail = mail + prod.getNome() + " " + prod.getQuantidade() + " " + prod.getUnidade() + " \n";
                        }
                        Intent sendIntent = new Intent();
                        Intent chooserIntent = Intent.createChooser(sendIntent, "Selecionar programa de email");
                        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{selectm});
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Lista de compra, data:" + finalListaCompras.getData());
                        sendIntent.putExtra(Intent.EXTRA_TEXT,mail);
                        sendIntent.setType("message/rfc822");
                        if (sendIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(chooserIntent);
                        }
                    }
                });
                builder.create();
                builder.show();
                }
        });
        comprada = (Button )findViewById(R.id.list_comprada);
        comprada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Produtos> produtosc = dao.list();
                ListaCompras listaComprasc = dao2.getMaxID();
                ArrayList<ListaComprasProdutos> lprodutosc = dao3.listIDrecipe(listaComprasc.getID());
                for(int i = 0; i < produtosc.size() ; i ++){
                    Produtos prc = produtosc.get(i);
                    for (int j = 0; j < lprodutosc.size(); j ++){
                        ListaComprasProdutos prcl = lprodutosc.get(j);
                        if(prcl.getNome().equalsIgnoreCase(prc.getNome())){
                            prc.setQuantidade(prc.getQuantidade()+prcl.getQuantidade());
                            dao.editprodquant(prc,prc.getID());
                        }
                    }
                }
                listaComprasc.setData(date);
                ListaCompras newLista = new ListaCompras();
                newLista.setData(date);
                newLista.setPreco(0);
                dao2.add(newLista);
                startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));

            }
        });
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
        if(id == R.id.action_add){
            startActivity(new Intent(this,ListadeComprasInsert.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
