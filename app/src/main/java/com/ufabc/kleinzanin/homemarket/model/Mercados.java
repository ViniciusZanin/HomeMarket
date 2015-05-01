package com.ufabc.kleinzanin.homemarket.model;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by Felippe on 17/04/2015.
 */
public class Mercados {
    private int ID;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private LatLng position;

    public String getNome(){ return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEmail(){ return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelefone(){ return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco(){ return endereco; }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
