package com.ufabc.kleinzanin.homemarket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felippe on 29/04/2015.
 */
public class IngredientesTempDAO {
    private static IngredientesTempDAO dao;
    private List<IngredientesTemp> ingredientes;

    protected IngredientesTempDAO(){}

    private void init(){
        ingredientes = new ArrayList<>();
    }

    public static IngredientesTempDAO newInstance() {
        if (dao == null) {
            dao = new IngredientesTempDAO();
            dao.init();
        }

        return dao;
    }

    public void add(IngredientesTemp ingrediente) {
        ingredientes.add(ingrediente);
    }

    public boolean remove(int position) {
        boolean res = true;

        try {
            ingredientes.remove(position);
        } catch(IndexOutOfBoundsException e) {
            res = false;
        }

        return res;
    }

    public boolean removeAll() {
        boolean status = true;
        ingredientes.clear();

        return status;
    }

    public IngredientesTemp[] list(IngredientesTemp ingrediente) {
        return ingredientes.toArray(new IngredientesTemp[ingredientes.size()]);
    }

    public int size() {
        return ingredientes.size();
    }

    public IngredientesTemp getItemAt(int pos) {
        return ingredientes.get(pos);
    }

}
