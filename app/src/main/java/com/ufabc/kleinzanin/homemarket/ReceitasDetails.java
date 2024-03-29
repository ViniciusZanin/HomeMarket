package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.util.ArrayList;


public class ReceitasDetails extends ActionBarActivity {

    private ReceitasDetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_details);
        detail = (ReceitasDetailFragment ) getFragmentManager().findFragmentById(R.id.receita_fragmentdetails);
        showReceita();

    }

    private void showReceita() {
        ArrayList<Receitas> receitas;
        ReceitasDAO dao = ReceitasDAO.newInstance(this);
        receitas = dao.list();
        int pos = getIntent().getExtras().getInt("receitaPosition");
        int buttonDisp = getIntent().getExtras().getInt("dispButton");
        Receitas receita = receitas.get(pos);
        detail.showReceitas(receita, buttonDisp);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receita_details, menu);
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

        else if(id == R.id.receita_remove){
            ArrayList<Receitas> receitas;
            ReceitasDAO dao = ReceitasDAO.newInstance(this);
            receitas = dao.list();
            int pos = getIntent().getExtras().getInt("receitaPosition");
            Receitas receita = receitas.get(pos);
            IngredientesDAO dao_ing = IngredientesDAO.newInstance(this);
            dao_ing.removeReceitaIngs(receita.getID());
            dao.remove(pos);
            Intent intent = new Intent(this, ReceitasMain.class);
            startActivity(intent);
        }

        else if(id == R.id.receita_edit){
            int pos = getIntent().getExtras().getInt("receitaPosition");
            Intent intent = new Intent(this, ReceitasEdit.class);
            intent.putExtra("receitaPosition", pos);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
