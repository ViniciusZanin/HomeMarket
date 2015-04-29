package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ufabc.kleinzanin.homemarket.adapter.DespensaAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.ProdutoAdapter;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;


public class Despensa extends ActionBarActivity {
    private ProdutosDao dao;
    private ListView listView;
    private Button add;
    private Button remove;

    private DespensaDetailFragment detailFragment;
    private DespensaListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despensa);
        init();
        setupProdutoList();
    }

    private void init(){
        this.dao = ProdutosDao.newInstance(this);

        listFragment = (DespensaListFragment )getFragmentManager().findFragmentById(R.id.fragment_list);
        detailFragment = (DespensaDetailFragment ) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if(listFragment != null){
            listView = (ListView )listFragment.getView().findViewById(R.id.list_despensa);
        }


    }

    private void setupProdutoList(){
        listView = (ListView )findViewById(R.id.list_despensa);
        final Despensa self = this;
        listView.setAdapter(new DespensaAdapter(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_despensa, menu);
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
        if(id == R.id.action_despensa_add){
            confirmFireMissiles();
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmFireMissiles() {
        DialogFragment newFragment = new FireMissilesDialogFragment();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Fire?")
                    .setPositiveButton("FIRE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
