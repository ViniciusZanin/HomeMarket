package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;
import com.ufabc.kleinzanin.homemarket.model.Produtos;

import java.util.ArrayList;

/**
 * Created by Felippe on 17/04/2015.
 */
public class MercadoAdapter extends BaseAdapter implements Filterable{
    private MercadoDAO dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<Mercados> mercados;
    private MercadoFilter mf;

    public MercadoAdapter(Context c){
        this.context = c;
        this.dao = MercadoDAO.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mercados = dao.list();
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

    public Mercados getItemAt(int position) { return mercados.get(position);}

    @Override
    public int getCount() {
        return mercados.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return mercados.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mercados mercado =  mercados.get(position);
        TextView nome = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.mercado_list_item, null);
        }
        nome = (TextView ) convertView.findViewById(R.id.mercado_name);
        nome.setText(mercado.getNome());

        return convertView;

    }

    public Filter getFilter() {
        if (mf == null) {
            mf = new MercadoFilter();
        }

        return mf;
    }

    private class MercadoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Mercados> tempList = new ArrayList<>();

                // search content in friend list
                for (Mercados m : mercados) {
                    if (m.getNome().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(m);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mercados.size();
                filterResults.values = mercados;
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
            mercados = (ArrayList<Mercados>) results.values;
            notifyDataSetChanged();
        }
    }
}
