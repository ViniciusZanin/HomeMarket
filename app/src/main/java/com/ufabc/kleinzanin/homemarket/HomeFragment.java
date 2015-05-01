package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.adapter.ListaDeCompraAdapter;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private static final String LOGTAG = HomeFragment.class.getSimpleName();
    private TextView statusDespensa, vStatusDespensa;
    private TextView statusCompraAtual, vStatusCompraAtual;
    private TextView statusUltimaCompra, vStatusUltimaCompra;
    ProdutosDao dao;
    ListaComprasDAO dao2;
    ListaComprasProdutosDAO dao3;


	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        statusDespensa = (TextView)rootView.findViewById(R.id.status_despensa);
        vStatusDespensa = (TextView)rootView.findViewById(R.id.percent_despensa);
        statusCompraAtual = (TextView)rootView.findViewById(R.id.compra_atual);
        vStatusCompraAtual = (TextView)rootView.findViewById(R.id.valor_compra_atual);
        statusUltimaCompra = (TextView)rootView.findViewById(R.id.ultima_compra);
        vStatusUltimaCompra = (TextView)rootView.findViewById(R.id.valor_ultima_compra);

        getData();
        return rootView;
    }

    private void getData(){
        double qa = 0 ;
        double qm = 0 ;
        ArrayList<Produtos> produtos;
        dao = ProdutosDao.newInstance(getActivity());
        Produtos produto;
        produtos = dao.despensa_porcentagem();
          for (int i = 0; i < produtos.size(); i ++) {
           produto = produtos.get(i);
           qa = qa + produto.getQuantidade();
           qm = qm + produto.getConsumo();
       }
        DecimalFormat fmt = new DecimalFormat("0.00");
        double porcentagem = (qa/qm)*100;
        if(porcentagem <= 0){ porcentagem = 0; }
        if (porcentagem >=100) { porcentagem = 100;}
        String porct = fmt.format(porcentagem);

        String date;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        date = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR));
        statusDespensa.setText("Status da Despensa em " + date);
        vStatusDespensa.setText(String.valueOf(porct)+"%");



        /* GetData Lista */
        dao2 = ListaComprasDAO.newInstance(getActivity());
        dao3 = ListaComprasProdutosDAO.newInstance(getActivity());
        ListaCompras listaCompras = dao2.getMaxID();
        if(listaCompras.getID() == 0){
            ListaCompras firstLista = new ListaCompras();
            firstLista.setData("0");
            firstLista.setPreco(0);
            dao2.add(firstLista);
            listaCompras = dao2.getMaxID();
        }
        ArrayList<Produtos> produtos2;
        produtos2 = dao.lista_compras_prod_missing();
        Log.e(LOGTAG,String.valueOf(listaCompras.getID()) + " " + listaCompras.getData());
        ArrayList<ListaComprasProdutos> testList = dao3.list();
        if(testList.size() == 0){
            for(int i = 0; i < produtos2.size();i++ ){
                Produtos produto2 = produtos2.get(i);
                ListaComprasProdutos newlprod = new ListaComprasProdutos();
                newlprod.setNome(produto2.getNome());
                newlprod.setQuantidade(produto2.getConsumo()-produto2.getQuantidade());
                newlprod.setPreco((produto2.getConsumo()-produto2.getQuantidade())*produto2.getPreço());
                Log.e(LOGTAG, produto2.getUnidade());
                newlprod.setUnidade(produto2.getUnidade());
                Log.e(LOGTAG, newlprod.getUnidade());
                newlprod.setLista_id(listaCompras.getID());
                dao3.add(newlprod);
            }
        }

        ArrayList<ListaComprasProdutos> listaComprasProdutoses = dao3.listIDrecipe(listaCompras.getID());


        for(int i = 0; i < produtos2.size(); i++) {
            if (produtos2.size() > 0) {
                Produtos produto2 = produtos2.get(i);
                boolean equal = false;
                for (int j = 0; j < listaComprasProdutoses.size(); j++) {
                    ListaComprasProdutos lprod = listaComprasProdutoses.get(j);
                    Log.e(LOGTAG, lprod.getNome() + " " + String.valueOf(lprod.getLista_id()) + String.valueOf(lprod.getID()));
                    if (produto2.getNome().equalsIgnoreCase(lprod.getNome()) && ((produto2.getConsumo() - produto2.getQuantidade()) != lprod.getQuantidade())) {
                        lprod.setQuantidade(produto2.getConsumo() - produto2.getQuantidade());
                        lprod.setPreco((produto2.getConsumo() - produto2.getQuantidade()) * produto2.getPreço());
                        dao3.edit(lprod);
                        equal = true;
                    } else if (produto2.getNome().equalsIgnoreCase(lprod.getNome()) && ((produto2.getConsumo() - produto2.getQuantidade() * produto2.getPreço()) != lprod.getPreco())) {
                        lprod.setQuantidade(produto2.getConsumo() - produto2.getQuantidade());
                        lprod.setPreco((produto2.getConsumo() - produto2.getQuantidade()) * produto2.getPreço());
                        dao3.edit(lprod);
                        equal = true;
                    } else if (produto2.getNome().equalsIgnoreCase(lprod.getNome())) {
                        equal = true;
                    }
                }
                Log.e(LOGTAG, String.valueOf(equal));
                if (equal == false) {
                    ListaComprasProdutos newlprod = new ListaComprasProdutos();
                    newlprod.setNome(produto2.getNome());
                    newlprod.setQuantidade(produto2.getConsumo() - produto2.getQuantidade());
                    newlprod.setPreco((produto2.getConsumo() - produto2.getQuantidade()) * produto2.getPreço());
                    newlprod.setUnidade(produto2.getUnidade());
                    newlprod.setLista_id(listaCompras.getID());
                    dao3.add(newlprod);
                }
            }
        }
        double preço = 0;
        for(int i = 0; i < listaComprasProdutoses.size(); i ++){
            ListaComprasProdutos liprod = listaComprasProdutoses.get(i);
            preço = preço + (liprod.getPreco());
            listaCompras.setPreco(preço);
            listaCompras.setData(date);
            dao2.editpreco(listaCompras);
        }


        String pr = fmt.format(listaCompras.getPreco());


        statusCompraAtual.setText("Preço da Lista de Compra Atual");
        vStatusCompraAtual.setText("R$"+pr); //TODO:Implements method to get lista de compra value

        statusUltimaCompra.setText("Gasto na Ultima Compra");
        vStatusUltimaCompra.setText("R$100,30"); //TODO:Implements method to get LAST lista de compra value

        statusDespensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Despensa.class);
                startActivity(intent);
            }
        });

        vStatusDespensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Despensa.class);
                startActivity(intent);
            }
        });
        statusCompraAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListadeCompras.class);
                startActivity(intent);
            }
        });
        vStatusCompraAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListadeCompras.class);
                startActivity(intent);
            }
        });
        vStatusUltimaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListadeCompras.class);
                startActivity(intent);
            }
        });
        statusUltimaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListadeCompras.class);
                startActivity(intent);
            }
        });


    }
}
