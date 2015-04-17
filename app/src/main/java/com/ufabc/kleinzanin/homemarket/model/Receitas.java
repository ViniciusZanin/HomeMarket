package com.ufabc.kleinzanin.homemarket.model;

import java.util.List;

/**
 * Created by Felippe on 16/04/2015.
 */
public class Receitas {
    private String receita;
    private String ingredientes;
    private String modopreparo;

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getModopreparo() {
        return modopreparo;
    }

    public void setModopreparo(String modopreparo) {
        this.modopreparo = modopreparo;
    }

    public String toString() {
        return receita;
    }
}
