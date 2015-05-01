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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MercadoInsert extends ActionBarActivity {
    private EditText editText;
    private static final String LOGTAG = MercadoInsert.class.getSimpleName();
    private MercadoDAO dao = MercadoDAO.newInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_insert);
    }

    private void insert(){

            boolean nameok = false, emailok = false, phoneok = false, posok = false;
            String nome = ((EditText)findViewById(R.id.insert_name)).getText().toString();
            String email = ((EditText)findViewById(R.id.insert_email)).getText().toString();
            String telefone = ((EditText)findViewById(R.id.insert_telefone)).getText().toString();
            String endereco = ((EditText)findViewById(R.id.insert_endereco)).getText().toString();

            Mercados mercado = new Mercados();
            if (nome.length() > 0) {
                mercado.setNome(nome);
                nameok = true;
            } else {
                editText = (EditText) findViewById(R.id.insert_name);
                editText.setError("Nome invalido.");
            }
            if(isValidEmail(email)) {
                mercado.setEmail(email);
                emailok = true;
            }else{
                editText = (EditText) findViewById(R.id.insert_email);
                editText.setError("Email invalido");
            }
            if(isValidPhone(telefone)) {
                mercado.setTelefone(telefone);
                phoneok = true;
            }else{
                editText = (EditText) findViewById(R.id.insert_telefone);
                editText.setError("Telefone invalido");
            }

            mercado.setEndereco(endereco);
            LatLng latLng = Coordinates(endereco);
            if (latLng == null) {
                editText = (EditText) findViewById(R.id.insert_endereco);
                editText.setError("Endereco invalido");
                editText.setHint("Tente: Rua xxx, 1234, Sao Paulo, SP");

            }else {
                mercado.setPosition(latLng);
                posok = true;
            }

            if((nameok) && (phoneok) && (emailok) && (posok)) {
                dao.add(mercado);
                Toast.makeText(this, getString(R.string.insert_status_ok), Toast.LENGTH_SHORT).show();
                startActivity((new Intent(this, MercadoMain.class)));
            }
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
