package com.ufabc.kleinzanin.homemarket.model;

/**
 * Created by Vinicius on 30/04/2015.
 */
public class ListaCompras {
    private int ID;
    private double preco;
    private String data;

    public int getID(){return ID;}

    public void setID(int ID){ this.ID = ID;}

    public double getPreco(){return preco;}

    public void setPreco(double preco){this.preco = preco;}

    public String getData() { return data;}

    public void setData(String data) {this.data = data;}
}
