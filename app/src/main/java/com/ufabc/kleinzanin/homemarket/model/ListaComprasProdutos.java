package com.ufabc.kleinzanin.homemarket.model;

/**
 * Created by Vinicius on 30/04/2015.
 */
public class ListaComprasProdutos {
    private int ID;
    private String nome;
    private double quantidade;
    private double preco;
    private int lista_id;
    private String unidade;


    public int getID(){return ID;}

    public void setID(int ID){this.ID = ID;}

    public String getNome(){return nome;}

    public void setNome(String nome) { this.nome = nome; }

    public double getQuantidade() { return quantidade;}

    public void setQuantidade(double quantidade){ this.quantidade = quantidade;}

    public double getPreco() {return preco;}

    public void setPreco(double preco){this.preco = preco;}

    public int getLista_id(){return lista_id;}

    public void setLista_id(int lista_id){this.lista_id = lista_id;}

    public String getUnidade(){return unidade;}

    public void setUnidade(String unidade){this.unidade = unidade;}

}
