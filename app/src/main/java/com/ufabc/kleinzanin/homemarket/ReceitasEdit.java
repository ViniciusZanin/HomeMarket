package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.util.ArrayList;

public class ReceitasEdit extends ActionBarActivity {

    private ReceitasDAO dao =  ReceitasDAO.newInstance(this);
    private TextView ingredientes;
    private EditText nome;
    private EditText modprep;
    private Button save;
    private Button add;
    String ingrediente = "";
    String ind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas_edit);
        init();
    }

    private void init() {
        ArrayList<Receitas> receitas;
        ReceitasDAO dao = ReceitasDAO.newInstance(this);
        receitas = dao.list();
        int pos = getIntent().getExtras().getInt("receitaPosition");
        Receitas receita = receitas.get(pos);
        ingredientes = (TextView )findViewById(R.id.ingredientes_list);
        modprep = (EditText) findViewById(R.id.Mod_prepedit);
        nome =  ((EditText) findViewById(R.id.Nome_editText));
        save = (Button )findViewById(R.id.ReceitaInsert_button);
        add = (Button ) findViewById(R.id.Add_button);
        nome.setText(receita.getReceita());
        modprep.setText(receita.getModopreparo());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ind = ((EditText) findViewById(R.id.insert_ingrediente)).getText().toString();
                ingrediente = ingrediente + ind;
                ingrediente = ingrediente + "\n";
                ingredientes.setText(ingrediente);
                ((EditText) findViewById(R.id.insert_ingrediente)).setText(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receitas_edit, menu);
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
    private void edit(){
        ArrayList<Receitas> receitas;
        ReceitasDAO dao = ReceitasDAO.newInstance(this);
        receitas = dao.list();
        int pos = getIntent().getExtras().getInt("receitaPosition");
        Receitas receita = receitas.get(pos);
        String nnome = ((EditText) findViewById(R.id.Nome_editText)).getText().toString();
        String nmodepreparo = ((EditText)findViewById(R.id.Mod_prepedit)).getText().toString();

        receita.setModopreparo(nmodepreparo);
        receita.setReceita(nnome);

        dao.edit(receita);
        Toast.makeText(this, "Redceita Editada", Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,ReceitasMain.class)));

    }
}
