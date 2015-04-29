package com.ufabc.kleinzanin.homemarket.model;

/**
 * Created by Felippe on 29/04/2015.
 */
public class Ingredientes {
    private int ID;
    private String nome;
    private int quantidade;
    private String unidade;
    private int receitaID;

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getReceitaID(){return receitaID;}

    public void setReceitaID(int receitaID){this.receitaID = receitaID;}
}
