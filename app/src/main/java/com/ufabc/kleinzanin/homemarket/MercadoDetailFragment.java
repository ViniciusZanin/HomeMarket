package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.Mercados;


public class MercadoDetailFragment extends Fragment {

    private TextView nome;
    private TextView telefone;
    private TextView email;
    private TextView endereco;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mercado_detail_fragment, container, false);
    }

    private void init(){
        nome = (TextView )getView().findViewById(R.id.mercado_item_nome);
        email = (TextView)getView().findViewById(R.id.mercado_item_email);
        telefone = (TextView)getView().findViewById(R.id.mercado_item_telefone);
        endereco = (TextView)getView().findViewById(R.id.mercado_item_endereco);

    }

    public void showMercado(Mercados mercado){
        init();
        nome.setText(mercado.getNome());
        email.setText(mercado.getEmail());
        telefone.setText(mercado.getTelefone());
        endereco.setText(mercado.getEndereco());
    }
}
