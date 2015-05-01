package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.adapter.ReceitasDisponivelAdapter;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;


public class ReceitasDisponiveis extends ActionBarActivity {

    private ListView listView;
    private ReceitasDetailFragment detailFragment;
    ReceitasDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas_disponiveis);
        listView = (ListView )findViewById(R.id.receitadisp_list);
        listView.setAdapter(new ReceitasDisponivelAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (detailFragment == null) {
                    Intent intent = null;

                    intent = new Intent(parent.getContext(), ReceitasDetails.class);
                    intent.putExtra("receitaPosition", position);
                    intent.putExtra("dispButton", 1);
                    startActivity(intent);
                } else {
                    Receitas receitas = dao.getItemAt(position);

                    detailFragment.showReceitas(receitas, 1);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receitas_disponiveis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
