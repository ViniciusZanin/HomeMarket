package com.ufabc.kleinzanin.homemarket;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Mercados;

import java.util.ArrayList;


public class MercadoMaps extends ActionBarActivity implements OnMapReadyCallback {
    private static final String LOGTAG = MercadoMaps.class.getSimpleName();
    private MapFragment mapFragment;
    private GoogleMap googleMaps;
    private MercadoDAO mercadoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_maps);

        mapFragment = (MapFragment )getFragmentManager().findFragmentById(R.id.maps_fragment);
        mapFragment = genDefaultMap();
        mapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction().add(R.id.maps_fragment,mapFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mercado_maps, menu);
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

    private MapFragment genDefaultMap () {
        GoogleMapOptions mapOptions = new GoogleMapOptions();

        mapOptions.camera(new CameraPosition(new LatLng(-23.551580, -46.633079),10f,0f,0f));

        mapOptions.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .scrollGesturesEnabled(true)
                .zoomControlsEnabled(true)
                .zoomGesturesEnabled(true);

        return MapFragment.newInstance(mapOptions);
    }

    public void onMapReady(GoogleMap googleMap) {
        // enable "My location" layer
        mapFragment.getMap().setMyLocationEnabled(true);

        this.googleMaps = googleMap;
        addMarkers();
    }

    public void addMarkers(){
        mercadoDAO = new MercadoDAO(this);
        ArrayList<Mercados> markets = mercadoDAO.list();
        Mercados mercado = new Mercados();
        int i = 0;
        MarkerOptions markerOptions = new MarkerOptions ();

        while(i < markets.size()){
            mercado = markets.get(i);
            markerOptions.position(mercado.getPosition());
            markerOptions.title(mercado.getNome());
            markerOptions.snippet("Telefone: " +mercado.getTelefone()+"\n Endereco: "+mercado.getEndereco());
            googleMaps.addMarker(markerOptions);
            i++;
        }
    }

}
