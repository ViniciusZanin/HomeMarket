package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Receitas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReceitasDetailFragment extends Fragment {

    private TextView ingredientes;
    private TextView modprep;
    private Button realizada;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receitas_detail, container, false);
    }

    private void init() {
        ingredientes = (TextView) getView().findViewById(R.id.ingredientes_list);
        modprep = (TextView) getView().findViewById(R.id.Mod_prep);
    }

    public void showReceitas(Receitas receitas) {
        init();
        final ReceitasDetailFragment self = this;
        ingredientes.setText((receitas.getIngredientes()));
        modprep.setText(receitas.getModopreparo());

    }
}
