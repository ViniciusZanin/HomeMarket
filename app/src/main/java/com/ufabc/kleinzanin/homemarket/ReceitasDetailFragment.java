package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;


public class ReceitasDetailFragment extends Fragment {

    private ListView listView;
    private TextView modprep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_receitas_detail_fragment, container, false);
    }

    private void init(){
        listView = (ListView )getView().findViewById(R.id.ingredientes_list);
        modprep = (TextView )getView().findViewById(R.id.Mod_prep);


    }

    public void showReceitas(Receitas receitas){
        init();
        final ReceitasDetailFragment self = this;

        listView.setAdapter(new ReceitasAdapter(this));
         modprep.setText(receitas.getModopreparo());


    }

}
