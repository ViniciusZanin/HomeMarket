package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;

import java.util.ArrayList;

/**
 * Created by Felippe on 29/04/2015.
 */
public class IngredientesAdapter extends BaseAdapter{
    private IngredientesDAO dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<Ingredientes> ingredientes;

    public  IngredientesAdapter(Context c){
        this.context = c;
        this.dao = IngredientesDAO.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ingredientes = dao.list();
    }

    public boolean remove(int position){
        boolean status = dao.remove(position);
        if (status)
            this.notifyDataSetChanged();

        return status;
    }

    public void add(Ingredientes ingrediente){
        dao.add(ingrediente);
        this.notifyDataSetChanged();
    }

    public Ingredientes getItemAt(int position) { return ingredientes.get(position);}

    @Override
    public int getCount() { return ingredientes.size(); }

    @Override
    public Object getItem(int position) {
        return getItemAt(position);
    }

    @Override
    public long getItemId(int position) { return ingredientes.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredientes ingrediente = ingredientes.get(position);
        TextView nome = null;
        TextView quantidade = null;
        TextView unidade = null;
        Button remove;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.despensa_list_item,null);
        }
        nome = (TextView )convertView.findViewById(R.id.ingrediente_name);
        quantidade = (TextView ) convertView.findViewById(R.id.ingrediente_quant);
        unidade = (TextView )convertView.findViewById(R.id.ingrediente_uni);
        remove = (Button )convertView.findViewById(R.id.ing_remove_button);
        nome.setText(ingrediente.getNome());
        quantidade.setText(ingrediente.getQuantidade());
        unidade.setText(ingrediente.getUnidade());
        return convertView;
    }
}
