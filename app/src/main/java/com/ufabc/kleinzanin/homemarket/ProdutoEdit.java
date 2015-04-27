package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDAO;


public class ProdutoEdit extends ActionBarActivity {

    private EditText nome;
    private EditText quantidade;
    private EditText preço;
    private EditText consumo;
    private ImageView produtoimage;
    private CheckBox produtoCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_edit);
        init();
    }


    private void init() {
        ProdutosDAO dao = ProdutosDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Produtos produto = dao.getItemAt(pos);
        nome = (EditText) findViewById(R.id.edit_produto_nome);
        quantidade = (EditText ) findViewById(R.id.edit_produto_quantidade);
        preço = (EditText ) findViewById(R.id.edit_produto_preço);
        consumo = (EditText ) findViewById(R.id.edit_produto_consumo);
        produtoimage = (ImageView ) findViewById(R.id.edit_produto_imagem);
        produtoCheck = (CheckBox) findViewById(R.id.edit_produto_check);
        nome.setText(produto.getNome());
        quantidade.setText(Integer.toString(produto.getQuantidade()));
        preço.setText(produto.getPreço());
        produtoimage.setImageResource(produto.getImagem());
        produtoCheck.setChecked(produto.getChecked());
        if(produtoCheck.isChecked()){
            consumo.setVisibility(View.VISIBLE);
            consumo.setText(Integer.toString(produto.getConsumo()));
        }
    }

    private void editProduto(){
        ProdutosDAO dao = ProdutosDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Produtos produto = dao.getItemAt(pos);
        String nnome = ((EditText ) findViewById(R.id.edit_produto_nome)).getText().toString();
        String nquantidade = ((EditText )findViewById(R.id.edit_produto_quantidade)).getText().toString();
        String npreço = ((EditText )findViewById(R.id.edit_produto_preço)).getText().toString();
        String nconsumo = ((EditText )findViewById(R.id.edit_produto_consumo)).getText().toString();
        boolean ncheck = ((CheckBox )findViewById(R.id.edit_produto_check)).isChecked();
        produto.setNome(nnome);
        produto.setQuantidade(Integer.parseInt(nquantidade));
        produto.setPreço(npreço);
        if(ncheck){
            consumo.setVisibility(View.VISIBLE);
            produto.setConsumo(Integer.parseInt(nconsumo));}
        produto.setChecked(ncheck);
        dao.edit(produto);
        Toast.makeText(this, "Produto Editado", Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,Despensa.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produto_edit, menu);
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
        if(id == R.id.action_edit_produto){
            editProduto();
        }

        return super.onOptionsItemSelected(item);
    }
}
