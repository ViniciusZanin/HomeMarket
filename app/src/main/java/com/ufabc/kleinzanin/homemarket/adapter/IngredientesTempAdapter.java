package com.ufabc.kleinzanin.homemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesTemp;
import com.ufabc.kleinzanin.homemarket.model.IngredientesTempDAO;

/**
 * Created by Felippe on 29/04/2015.
 */
public class IngredientesTempAdapter extends BaseAdapter {
    private IngredientesTempDAO dao;
    private Context context;

    public IngredientesTempAdapter(Context context) {
        this.context = context;
        this.dao = IngredientesTempDAO.newInstance();

    }

    public boolean remove(int position) {
        boolean status = dao.remove(position);

        if (status)
            this.notifyDataSetChanged(); // update the view

        return status;
    }

    public void add(IngredientesTemp ingrediente) {
        dao.add(ingrediente);
        this.notifyDataSetChanged();
    }
    public IngredientesTemp getItemAt(int position) {
        return dao.getItemAt(position);
    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        IngredientesTemp ingrediente = null;
        TextView nome = null;
        TextView quantidade = null;
        TextView unidade = null;
        ImageButton remove;
        LayoutInflater inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.ingredientes_list_item, null);
        }
        ingrediente = dao.getItemAt(position);
        nome = (TextView )convertView.findViewById(R.id.ingrediente_name);
        quantidade = (TextView )convertView.findViewById(R.id.ingrediente_quant);
        unidade = (TextView )convertView.findViewById(R.id.ingrediente_uni);
        nome.setText(ingrediente.getNome());
        quantidade.setText(String.valueOf(ingrediente.getQuantidade()));
        unidade.setText(ingrediente.getUnidade());
        remove = (ImageButton )convertView.findViewById(R.id.ingrediente_button_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        return convertView;
    }
}
