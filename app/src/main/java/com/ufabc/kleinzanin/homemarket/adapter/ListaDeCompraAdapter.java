package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.Produto;
import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutosDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Vinicius on 17/04/2015.
 */
public class ListaDeCompraAdapter extends BaseAdapter {
    private static final String LOGTAG =  ListaDeCompraAdapter.class.getSimpleName();
    private ListaComprasProdutosDAO dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<ListaComprasProdutos> produtos;

    public  ListaDeCompraAdapter(Context c,int ID){
        this.context = c;
        this.dao = ListaComprasProdutosDAO.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = dao.listIDrecipe(ID);
    }

    public boolean remove(int position){
        boolean status = dao.remove(position);
        if (status)
            this.notifyDataSetChanged();

        return status;
    }

    public void add(ListaComprasProdutos produto){
        dao.add(produto);
        this.notifyDataSetChanged();
    }

    public ListaComprasProdutos getItemAt(int position) { return dao.getItemAt(position);}

    @Override
    public int getCount() {
        return dao.size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaComprasProdutos produto = produtos.get(position);
        TextView nome = null;
        TextView quantidade = null;
        TextView preco = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.lista_compras_item,null);
        }

        nome = (TextView )convertView.findViewById(R.id.produto_nome);
        quantidade = (TextView ) convertView.findViewById(R.id.produto_quantidade);
        preco = (TextView) convertView.findViewById(R.id.produto_preço);
       nome.setText(produto.getNome());
        DecimalFormat fmt = new DecimalFormat("0.00");
        String str = fmt.format(produto.getPreco());
        quantidade.setText(String.valueOf(produto.getQuantidade()) + " " + produto.getUnidade());
        preco.setText("R$" + str);
        return convertView;
    }
}
