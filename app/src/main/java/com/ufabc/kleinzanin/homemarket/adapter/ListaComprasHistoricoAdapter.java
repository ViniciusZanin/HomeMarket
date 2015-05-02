package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.ListaCompras;
import com.ufabc.kleinzanin.homemarket.model.ListaComprasDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Vinicius on 01/05/2015.
 */
public class ListaComprasHistoricoAdapter extends BaseAdapter {
    ListaComprasDAO dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<ListaCompras> listaCompras;

    public ListaComprasHistoricoAdapter(Context c){
        this.context = c;
        this.dao = ListaComprasDAO.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listaCompras = dao.list();

    }
    public boolean remove(int position){
        boolean status = dao.remove(position);

        if(status) this.notifyDataSetChanged();

        return status;
    }

    public void add(ListaCompras listaCompras){
        dao.add(listaCompras);
        this.notifyDataSetChanged();
    }

    public ListaCompras getItemAt(int position) { return listaCompras.get(position);}

    @Override
    public int getCount() {
        return listaCompras.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return listaCompras.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaCompras listaCompra =  listaCompras.get(position);
        TextView nome = null;
        TextView preco = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.despensa_add_list_item, null);
        }
        nome = (TextView ) convertView.findViewById(R.id.produto_nome);
        nome.setText(listaCompra.getData());
        preco = (TextView )convertView.findViewById(R.id.produto_quantidade);
        DecimalFormat fmt = new DecimalFormat("0.00");
        String price = fmt.format(listaCompra.getPreco());
        preco.setText("R$" + price);


        return convertView;

    }
}
