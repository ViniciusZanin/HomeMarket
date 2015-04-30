package com.ufabc.kleinzanin.homemarket.model;

import android.graphics.Bitmap;
import android.media.Image;

public class Produtos {
    private int ID;
    private String nome;
    private double quantidade;
    private double preço;
    private String imagem;
    private double consumo;
    private boolean checked;
    private String unidade;

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreço() {
        return preço;
    }

    public void setPreço(double preço) {
        this.preço = preço;
    }

    public boolean getChecked() { return checked;}

    public void setChecked(boolean check){
        this.checked = check;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public String getImagem() { return imagem;}

    public void setImagem(String imagem) { this.imagem = imagem;}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isChecked() {
        return checked;
    }
}

