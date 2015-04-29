package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    private TextView statusDespensa, vStatusDespensa;
    private TextView statusCompraAtual, vStatusCompraAtual;
    private TextView statusUltimaCompra, vStatusUltimaCompra;
    ProdutosDao produtosDAO;


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
        String date;
        Calendar c = Calendar.getInstance();
        date = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH)+1 + "/" + c.get(Calendar.YEAR));
        statusDespensa.setText("Status da Produto em " + date);
        vStatusDespensa.setText("65%");

        statusCompraAtual.setText("Preço da Lista de Compra Atual");
        vStatusCompraAtual.setText("R$32,50"); //TODO:Implements method to get lista de compra value

        statusUltimaCompra.setText("Gasto na Ultima Compra");
        vStatusUltimaCompra.setText("R$100,30"); //TODO:Implements method to get LAST lista de compra value

        statusDespensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Produto.class);
                startActivity(intent);
            }
        });

        vStatusDespensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Produto.class);
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
