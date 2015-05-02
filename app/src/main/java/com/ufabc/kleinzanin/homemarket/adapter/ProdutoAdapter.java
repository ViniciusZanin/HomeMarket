package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.Produto;
import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;

/**
 * Created by Vinicius on 17/04/2015.
 */
public class ProdutoAdapter extends BaseAdapter implements Filterable {
    private ProdutosDao dao;
    private Context context;
    private ProdutoFilter pf;
    LayoutInflater inflater;
    ArrayList<Produtos> produtos;

    public  ProdutoAdapter(Context c){
        this.context = c;
        this.dao = ProdutosDao.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = dao.list();
    }

    public ProdutoAdapter (Context c, ArrayList<Produtos> list){
        this.context = c;
        this.dao = ProdutosDao.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = list;
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.produto_list_item,null);
        }
        nome = (TextView )convertView.findViewById(R.id.produto_nome);
        nome.setText(produto.getNome());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (pf == null) {
            pf = new ProdutoFilter();
        }

        return pf;
    }

    private class ProdutoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Produtos> tempList = new ArrayList<>();

                // search content in friend list
                for (Produtos p : produtos) {
                    if (p.getNome().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(p);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = produtos.size();
                filterResults.values = produtos;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            produtos = (ArrayList<Produtos>) results.values;
            notifyDataSetChanged();
        }
    }
}
