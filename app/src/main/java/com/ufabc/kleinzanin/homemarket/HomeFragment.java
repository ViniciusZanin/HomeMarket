package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        date = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH)+1 + "/" + c.get(Calendar.YEAR));
        statusDespensa.setText("Status da Despensa em " + date);
        vStatusDespensa.setText(String.valueOf(porct)+"%");

        statusCompraAtual.setText("Preço da Lista de Compra Atual");
        vStatusCompraAtual.setText("R$32,50"); //TODO:Implements method to get lista de compra value

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
