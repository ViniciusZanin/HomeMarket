package com.ufabc.kleinzanin.homemarket.model;

import android.graphics.Bitmap;
import android.media.Image;

public class Produtos {
    private String nome;
    private String quantidade;
    private String preço;
    private Image imagem;
    private String consumo;
    private boolean checked;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPreço() {
        return preço;
    }

    public void setPreço(String preço) {
        this.preço = preço;
    }

    public boolean getChecked() { return checked;}

    public void setChecked(boolean check){
        this.checked = check;
    }
    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }
}

