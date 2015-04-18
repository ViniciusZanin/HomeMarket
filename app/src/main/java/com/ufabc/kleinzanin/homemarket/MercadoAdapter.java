package com.ufabc.kleinzanin.homemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;

/**
 * Created by Felippe on 17/04/2015.
 */
public class MercadoAdapter extends BaseAdapter {
    private MercadoDAO dao;
    private Context context;

    public MercadoAdapter(Context c){
        this.context = c;
        this.dao = MercadoDAO.newInstance();
    }

    public boolean remove(int position){
        boolean status = dao.remove(position);

        if(status) this.notifyDataSetChanged();

        return status;
    }

    public void add(Mercados mercado){
        dao.add(mercado);
        this.notifyDataSetChanged();
    }

    public Mercados getItemAt(int position) { return dao.getItemAt(position);}

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
        Mercados mercado = null;
        TextView nome = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.mercado_list_item, null);
        }
        mercado = dao.getItemAt(position);
        nome = (TextView ) convertView.findViewById(R.id.mercado_name);
        nome.setText(mercado.getNome());

        return convertView;

    }
}
