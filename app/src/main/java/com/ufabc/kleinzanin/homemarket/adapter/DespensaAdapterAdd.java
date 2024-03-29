package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;

/**
 * Created by Vinicius on 29/04/2015.
 */
public class DespensaAdapterAdd extends BaseAdapter {
    private ProdutosDao dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<Produtos> produtos;

    public  DespensaAdapterAdd(Context c){
        this.context = c;
        this.dao = ProdutosDao.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = dao.despensa_prod();
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
        quantidade = (TextView ) convertView.findViewById(R.id.produto_quantidade);
        nome.setText(produto.getNome());
        if(produto.getChecked() == true){
            quantidade.setText(Double.toString(produto.getQuantidade()) + "/" +
                    Double.toString(produto.getConsumo()) + " " + produto.getUnidade());}
        else{
            quantidade.setText(Double.toString(produto.getQuantidade()) + " " + produto.getUnidade());

        }
        return convertView;
    }
}
