package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.adapter.IngredientesTempAdapter;
import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;
import com.ufabc.kleinzanin.homemarket.model.IngredientesTemp;
import com.ufabc.kleinzanin.homemarket.model.IngredientesTempDAO;
import com.ufabc.kleinzanin.homemarket.model.Receitas;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.util.ArrayList;

public class ReceitasEdit extends ActionBarActivity {

    private ReceitasDAO dao =  ReceitasDAO.newInstance(this);
    private Button save;
    private Button add;
    final Context context = this;
    private Spinner units;
    private IngredientesTempDAO dao_temp;
    private ListView listView;
    private IngredientesDAO ing_dao;

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

        ArrayList<Ingredientes> ingredientes;
        IngredientesDAO dao_ing = IngredientesDAO.newInstance(this);
        IngredientesTempDAO dao_temp = IngredientesTempDAO.newInstance();
        ingredientes = dao_ing.receita_igredientes(receita.getID());
        for(int i = 0; i<ingredientes.size(); i++) {
            Ingredientes ingrediente = ingredientes.get(i);
            IngredientesTemp ingredientesTemp = new IngredientesTemp();
            ingredientesTemp.setNome(ingrediente.getNome());
            ingredientesTemp.setUnidade(ingrediente.getUnidade());
            ingredientesTemp.setQuantidade(ingrediente.getQuantidade());
            dao_temp.add(ingredientesTemp);
        }
        listView = (ListView )findViewById(R.id.ingredientes_list);
        listView.setAdapter(new IngredientesTempAdapter(this));
        final IngredientesTempAdapter adapter = (IngredientesTempAdapter ) listView.getAdapter();

        EditText modprep = (EditText) findViewById(R.id.Mod_prepedit);
        EditText nome =  ((EditText) findViewById(R.id.Nome_editText));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ReceitasEdit.this);
                LayoutInflater inflater = ReceitasEdit.this.getLayoutInflater();
                View promptsView = inflater.inflate(R.layout.dialog_ingrediente_insert_old, null);
                builder.setView(promptsView);
                builder.setPositiveButton(R.string.ing_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog) dialog;
                        EditText name, quantidade;
                        Spinner unit;
                        unit = (Spinner)f.findViewById(R.id.insert_unit);
                        name = (EditText )f.findViewById(R.id.ingrediente_name_edit);
                        quantidade = (EditText )f.findViewById(R.id.ingrediente_quant_edit);
                        IngredientesTemp ingrediente = new IngredientesTemp();
                        ingrediente.setNome(name.getText().toString());
                        ingrediente.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
                        ingrediente.setUnidade(String.valueOf(unit.getSelectedItem()));
                        adapter.add(ingrediente);

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                final AlertDialog alertDialog = builder.create();
                units= (Spinner) promptsView.findViewById(R.id.insert_unit);
                addListenerOnSpinnerItemSelection();
                units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                alertDialog.show();
            }
        });
    }
    public void addListenerOnSpinnerItemSelection() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unitis_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(adapter);
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
        int ReceitaID = receita.getID();
        dao.edit(receita);

        IngredientesTempDAO dao_temp = IngredientesTempDAO.newInstance();
        ing_dao = IngredientesDAO.newInstance(this);
        ing_dao.removeReceitaIngs(receita.getID());
        int tam = dao_temp.size();
        String ing_nome;
        double ing_quant;
        String ing_uni;
        for(int i = 0; i<tam; i++){
            IngredientesTemp ing_temp = dao_temp.getItemAt(i);
            Ingredientes add_ing = new Ingredientes();
            ing_nome = ing_temp.getNome().toString();
            ing_quant = ing_temp.getQuantidade();
            ing_uni = ing_temp.getUnidade().toString();
            add_ing.setNome(ing_nome);
            add_ing.setQuantidade(ing_quant);
            add_ing.setUnidade(ing_uni);
            add_ing.setReceitaID(ReceitaID);
            ing_dao.add(add_ing);
        }
        dao_temp.removeAll();

        Toast.makeText(this, "Redceita Editada", Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,ReceitasMain.class)));

    }
}
