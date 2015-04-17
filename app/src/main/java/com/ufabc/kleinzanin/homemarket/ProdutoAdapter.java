package com.ufabc.kleinzanin.homemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

/**
 * Created by Vinicius on 17/04/2015.
 */
public class ProdutoAdapter extends BaseAdapter {
    private ProdutosDao dao;
    private Context context;

    public  ProdutoAdapter(Context c){
        this.context = c;
        this.dao = ProdutosDao.newInstance();
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

    public Produtos getItemAt(int position) { return dao.getItemAt(position);}

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
        Produtos produto = null;
        TextView nome = null;
        TextView quantidade = null;
        TextView preço = null;
        CheckBox checked = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
         Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.despensa_list_item,null);
        }
        produto = dao.getItemAt(position);
        nome = (TextView )convertView.findViewById(R.id.produto_nome);
        quantidade = (TextView ) convertView.findViewById(R.id.produto_quantidade);
        nome.setText(produto.getNome());
        if(produto.getChecked() == true){
        quantidade.setText(Integer.toString(produto.getQuantidade()) + "/" +
                Integer.toString(produto.getConsumo()));}
        else{
            quantidade.setText(Integer.toString(produto.getQuantidade()));

    }
        return convertView;
    }
}
