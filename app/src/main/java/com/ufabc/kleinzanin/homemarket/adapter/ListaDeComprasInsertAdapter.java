package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasProdutos;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;

/**
 * Created by Vinicius on 01/05/2015.
 */
public class ListaDeComprasInsertAdapter extends BaseAdapter {
    private ProdutosDao dao;
    private Context context;
    ArrayList<Produtos> produtos;
    LayoutInflater inflater;

    public  ListaDeComprasInsertAdapter(Context c){
        this.context = c;
        this.dao = ProdutosDao.newInstance(c);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = dao.lista_compras_insert();
    }


    public boolean remove(int position){
        boolean status = dao.remove(position);
        if (status)
            this.notifyDataSetChanged();

        return status;
    }

    public void add(Produtos produto){
        dao.add(produto);
        this.notifyDataSetChanged();
    }

    public Produtos getItemAt(int position) { return produtos.get(position);}


    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produtos produto = produtos.get(position);
        TextView nome = null;
        TextView quantidade = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.despensa_add_list_item,null);
        }
        nome = (TextView )convertView.findViewById(R.id.produto_nome);
        nome.setText(produto.getNome());
        return convertView;
    }
}
