package com.ufabc.kleinzanin.homemarket.model;

import android.graphics.Bitmap;
import android.media.Image;

public class Produtos {
    private String nome;
    private int quantidade;
    private String preço;
    private int imagem;
    private int consumo;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
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
    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public int getImagem() { return imagem;}

    public void setImagem(int imagem) { this.imagem = imagem;}

}

