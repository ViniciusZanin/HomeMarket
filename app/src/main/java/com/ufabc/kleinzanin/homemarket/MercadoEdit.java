package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MercadoEdit extends ActionBarActivity {

    private static final String LOGTAG = MercadoEdit.class.getSimpleName();
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText endereco;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_edit);
        ini();
    }

    private void ini() {
        MercadoDAO dao = MercadoDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("mercadoPosition");
        Mercados mercado = dao.getItemAt(pos);
        nome = (EditText ) findViewById(R.id.editmercado_name);
        email = (EditText ) findViewById(R.id.editmercado_email);
        telefone = (EditText ) findViewById(R.id.editmercado_telefone);
        endereco = (EditText ) findViewById(R.id.editmercado_endereco);
        nome.setText(mercado.getNome());
        email.setText(mercado.getEmail());
        telefone.setText(mercado.getTelefone());
        endereco.setText(mercado.getEndereco());
    }

    private void insert() {
        boolean nameok = false, emailok = false, phoneok = false, posok = false;
        MercadoDAO dao = MercadoDAO.newInstance(this);
        int pos = getIntent().getExtras().getInt("mercadoPosition");
        Mercados mercado = dao.getItemAt(pos);
        String nnome = ((EditText)findViewById(R.id.editmercado_name)).getText().toString();
        String nemail = ((EditText)findViewById(R.id.editmercado_email)).getText().toString();
        String ntelefone = ((EditText)findViewById(R.id.editmercado_telefone)).getText().toString();
        String nendereco = ((EditText)findViewById(R.id.editmercado_endereco)).getText().toString();


        if (nome.length() > 0) {
            mercado.setNome(nnome);
            nameok = true;
        } else {
            editText = (EditText) findViewById(R.id.editmercado_name);
            editText.setError("Nome invalido.");
        }
        if(isValidEmail(nemail)) {
            mercado.setEmail(nemail);
            emailok = true;
        }else{
            editText = (EditText) findViewById(R.id.editmercado_email);
            editText.setError("Email invalido");
        }
        if(isValidPhone(ntelefone)) {
            mercado.setTelefone(ntelefone);
            phoneok = true;
        }else{
            editText = (EditText) findViewById(R.id.editmercado_telefone);
            editText.setError("Telefone invalido");
        }
        mercado.setEndereco(nendereco);
        LatLng latLng = Coordinates(nendereco);
        if (latLng == null) {
            editText = (EditText)findViewById(R.id.editmercado_endereco);
            editText.setError("Endereco invalido");
            editText.setHint("Tente: Rua xxx, 1234, Sao Paulo, SP");
        }else {
            mercado.setPosition(latLng);
            posok = true;
        }

        if((nameok) && (phoneok) && (emailok) && (posok)) {
            dao.edit(mercado);
            Toast.makeText(this, getString(R.string.insert_status_ok), Toast.LENGTH_SHORT).show();
            startActivity((new Intent(this, MercadoMain.class)));}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_insert, menu);
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
        else if(id == R.id.mercadoinsert_button){
            insert();
        }

        return super.onOptionsItemSelected(item);
    }

    public LatLng Coordinates(String endereco){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        LatLng position = null;
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(endereco, 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                position = new LatLng(latitude,longitude);
            } else {
                Log.e(LOGTAG, "Address not found.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return position;
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public final static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
}
