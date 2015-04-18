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
        String ingredientes = "Leite" + "\n" + "Ovo" + "\n" + "Farinha" + "\n";

        r.setReceita("Teste 1");
        r.setIngredientes(ingredientes);
        r.setModopreparo("Bata as claras em neve\n" +
                "Reserve\n" +
                "Bata bem as gemas com a margarina e o açúcar\n" +
                "Acrescente o leite e farinha aos poucos sem parar de bater\n" +
                "Por último agregue as claras em neve e o fermento\n" +
                "Coloque em forma grande de furo central untada e enfarinhada\n" +
                "Asse em forno médio, pré - aquecido, por aproximadamente 40 minutos\n" +
                "Quando espetar um palito e sair limpo estará assado \n ");

        receitas.add(r);

    }
    public void edit(Receitas receita){ receitas.remove(receita);receitas.add(receita);}


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
