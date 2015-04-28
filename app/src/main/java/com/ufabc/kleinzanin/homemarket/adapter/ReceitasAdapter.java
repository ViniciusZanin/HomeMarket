package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.ReceitasDetailFragment;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.util.ArrayList;

/**
 * Created by Felippe on 16/04/2015.
 */
public class ReceitasAdapter extends BaseAdapter {
    private ReceitasDAO dao;
    private Context context;
    ArrayList<Receitas> receitas;
    LayoutInflater inflater;

    public ReceitasAdapter(Context c){
        this.context = c;
        this.dao = ReceitasDAO.newInstance(c);
        inflater = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        receitas = dao.list();
    }

    public ReceitasAdapter(ReceitasDetailFragment receitasDetailFragment) {
        this.dao = ReceitasDAO.newInstance(context);
    }


    public boolean remove(int position){
        return true;
    }

    public void add(Receitas receitas){
        dao.add(receitas);
        this.notifyDataSetChanged();

    }
    public Receitas getItemAt(int position) { return receitas.get(position);}


    @Override
    public int getCount() { return receitas.size();    }


    @Override
    public Object getItem(int position) {
        return getItemAt(position);
    }

    @Override
    public long getItemId(int position) {return receitas.get(position).getID();   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Receitas receita = receitas.get(position);
        TextView receitastext = null;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.receitas_layout_list, null);
        }
        receitastext = (TextView )convertView.findViewById(R.id.Receitatext);
        receitastext.setText(receita.getReceita());

        return convertView;
        }

}
