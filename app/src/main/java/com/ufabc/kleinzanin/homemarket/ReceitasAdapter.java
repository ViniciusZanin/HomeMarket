package com.ufabc.kleinzanin.homemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.HomeMarketDao;
import com.ufabc.kleinzanin.homemarket.model.Mercados;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

/**
 * Created by Felippe on 16/04/2015.
 */
public class ReceitasAdapter extends BaseAdapter {
    private ReceitasDAO dao;
    private Context context;

    public ReceitasAdapter(Context c){
        this.context = c;
        this.dao = ReceitasDAO.newInstance();
    }

    public ReceitasAdapter(ReceitasDetailFragment receitasDetailFragment) {
        this.dao = ReceitasDAO.newInstance();
    }


    public boolean remove(int position){
        return true;
    }

    public void add(Receitas receitas){
        dao.add(receitas);
        this.notifyDataSetChanged();

    }
    public Receitas getItemAt(int position) { return dao.getItemAt(position);}


    @Override
    public int getCount() {
        return dao.size();
    }


    @Override
    public Object getItem(int position) {
        return getItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Receitas receitas = null;
        TextView receitastext = null;
        LayoutInflater inflater = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.receitas_layout_list, null);
        }
        receitas = dao.getItemAt(position);
        receitastext = (TextView )convertView.findViewById(R.id.Receitatext);
        receitastext.setText(receitas.getReceita());

        return convertView;
        }

}
