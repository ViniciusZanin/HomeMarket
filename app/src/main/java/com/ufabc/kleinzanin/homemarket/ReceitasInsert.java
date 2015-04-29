package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ReceitasInsert extends ActionBarActivity {

    private ReceitasDAO dao =  ReceitasDAO.newInstance(this);
    private TextView ingredientes;
    private Button save;
    private Button add;
    String ingrediente = "";
    String ind;
    ArrayList<String> ingr;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas_insert);
        init();
    }


    private void init() {
        ingredientes = (TextView )findViewById(R.id.ingredientes_list);
        save = (Button )findViewById(R.id.ReceitaInsert_button);
        add = (Button ) findViewById(R.id.Add_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        ingr = new ArrayList<>();
        i = 0;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ind = ((EditText) findViewById(R.id.insert_ingrediente)).getText().toString();
                ingr.add(ind);
                /*ingrediente = ingrediente + ind;
                ingrediente = ingrediente + "\n";*/
                while (i != ingr.size()){
                    ingrediente = ingrediente + ingr.get(i).toString() + "\n";
                    i++;
                }
                ingredientes.setText(ingrediente);
                ((EditText) findViewById(R.id.insert_ingrediente)).setText(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receitas_insert, menu);
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
    private void insert() {
        String nome = ((EditText) findViewById(R.id.Nome_editText)).getText().toString();
        String modepreparo = ((EditText)findViewById(R.id.Mod_prepedit)).getText().toString();

        Receitas receita = new Receitas();
        receita.setModopreparo(modepreparo);
        receita.setIngredientes(ingr.toString());
        receita.setReceita(nome);

        dao.add(receita);
        Toast.makeText(this, "Redceita Adicionada", Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,ReceitasMain.class)));

    }
}
