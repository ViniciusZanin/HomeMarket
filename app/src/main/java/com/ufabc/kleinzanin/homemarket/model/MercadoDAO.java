package com.ufabc.kleinzanin.homemarket.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felippe on 17/04/2015.
 */
public class MercadoDAO {
    private static MercadoDAO dao;
    private List<Mercados> mercados;

    protected MercadoDAO(){}

    private void init(){
        mercados = new ArrayList<>();
        loadTestData();
    }

    private void loadTestData() {
            Mercados m;

            m = new Mercados();

            m.setNome("Carre4");
            m.setEmail("carre4@carre4.com");
            m.setEndereco("Rua Teste");
            m.setTelefonee("123445");

    }

    public static MercadoDAO newInstance(){
        if(dao == null){
            dao = new MercadoDAO();
            dao.init();
        }
        return dao;
    }

    public void add (Mercados mercado){mercados.add(mercado); }

    public boolean remove(int position){
        boolean res = true;

        try {
            mercados.remove(position);
        } catch (IndexOutOfBoundsException e){
            res = false;
        }
        return res;
    }

    public Mercados[] list(Mercados mercado){
        return mercados.toArray(new Mercados[mercados.size()]);
    }

    public int size() {return mercados.size();}

    public Mercados getItemAt (int pos) {return mercados.get(pos);}

}
