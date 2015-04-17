package com.ufabc.kleinzanin.homemarket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felippe on 17/04/2015.
 */
public class ReceitasDAO {
    private static ReceitasDAO dao;
    private List<Receitas> receitas;

    protected ReceitasDAO(){}

    private void init(){
        receitas = new ArrayList<>();
        loadTestData();
    }

    private void loadTestData() {
        Receitas r;

        r = new Receitas();

        r.setReceita("Teste 1");
        r.setIngredientes("Ingrediente 1");
        r.setModopreparo("Modo preparo");

        receitas.add(r);

    }

    public static ReceitasDAO newInstance() {
        if(dao == null) {
            dao = new ReceitasDAO();
            dao.init();
        }
        return dao;

    }


    public void add(Receitas receita){ receitas.add(receita);   }

    public boolean remove(int position){
        boolean res = true;

        try {
            receitas.remove(position);
        } catch (IndexOutOfBoundsException e){
            res = false;
        }
        return res;
    }

    public Receitas[] list(Receitas receita){
        return receitas.toArray(new Receitas[receitas.size()]);
    }

    public int size(){return receitas.size();}

    public Receitas getItemAt(int pos){return receitas.get(pos); }


}
