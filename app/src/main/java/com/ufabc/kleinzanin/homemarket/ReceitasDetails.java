package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;


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
        ReceitasDAO dao = ReceitasDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("receitaPosition");
        Receitas receitas = dao.getItemAt(pos);

        detail.showReceitas(receitas);

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
            ReceitasDAO dao = ReceitasDAO.newInstance(this);
            int pos = getIntent().getExtras().getInt("receitaPosition");
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
