package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.adapter.IngredientesAdapter;
import com.ufabc.kleinzanin.homemarket.adapter.IngredientesTempAdapter;
import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;
import com.ufabc.kleinzanin.homemarket.model.Receitas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReceitasDetailFragment extends Fragment {

    private ListView ingredientes;
    private TextView modprep;
    private Button realizada;
    private TextView nome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receitas_detail, container, false);
    }

    private void init() {
        ingredientes = (ListView ) getView().findViewById(R.id.ingredientes_list);
        modprep = (TextView) getView().findViewById(R.id.Mod_prep);
        nome = (TextView )getView().findViewById(R.id.ingrediente_name);
        realizada = (Button) getView().findViewById(R.id.button_receita_done);
    }

    public void showReceitas(final Receitas receitas, int dispButton) {
        init();
        final ReceitasDetailFragment self = this;
        modprep.setText(receitas.getModopreparo());
        ingredientes.setAdapter(new IngredientesAdapter(getActivity(), receitas.getID()));
        nome.setText(receitas.getReceita());
<<<<<<< HEAD
        int dipButton = dispButton;
        if (dipButton == 1) {
            realizada.setVisibility(View.VISIBLE);
        }
        realizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Produtos> produtos;
                ProdutosDao dao_prod = ProdutosDao.newInstance(getActivity());
                produtos = dao_prod.despensa();
                ArrayList<Ingredientes> ingredientes;
                IngredientesDAO dao_ing = IngredientesDAO.newInstance(getActivity());
                ingredientes = dao_ing.receita_igredientes(receitas.getID());

                for(int i = 0; i<ingredientes.size(); i++) {
                    Ingredientes ingrediente = ingredientes.get(i);
                    String ing_uni = ingrediente.getUnidade();
                    double ing_quant = ingrediente.getQuantidade();
                    for (int j = 0; j < produtos.size(); j++) {
                        Produtos produto = produtos.get(j);
                        if (ingrediente.getNome().equalsIgnoreCase(produto.getNome())) {
                            if (ing_uni.equalsIgnoreCase(produto.getUnidade())) {
                                if (ing_quant <= produto.getQuantidade()) {
                                    produto.setQuantidade(produto.getQuantidade() - ing_quant);
                                    dao_prod.editprodquant(produto, produto.getID());
                                    Toast.makeText(getActivity(), "Receita realizada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MenuPrincipal.class));
                                } else {
                                    Toast.makeText(getActivity(), "Quantidade indisponivel na despensa", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), ReceitasMain.class));
                                }
                            } else if (ing_uni.equalsIgnoreCase("gramas") && produto.getUnidade().equalsIgnoreCase("kilogramas")) {
                                if (ing_quant / 1000 <= produto.getQuantidade()) {
                                    produto.setQuantidade(produto.getQuantidade() - (ing_quant / 1000));
                                    dao_prod.editprodquant(produto, produto.getID());
                                    Toast.makeText(getActivity(), "Receita realizada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MenuPrincipal.class));
                                } else {
                                    Toast.makeText(getActivity(), "Quantidade indisponivel na despensa", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), ReceitasMain.class));
                                }
                            } else if (ing_uni.equalsIgnoreCase("kilogramas") && produto.getUnidade().equalsIgnoreCase("gramas")) {
                                if (ing_quant * 1000 <= produto.getQuantidade()) {
                                    produto.setQuantidade(produto.getQuantidade() - (ing_quant * 1000));
                                    dao_prod.editprodquant(produto, produto.getID());
                                    Toast.makeText(getActivity(), "Receita realizada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MenuPrincipal.class));
                                } else {
                                    Toast.makeText(getActivity(), "Quantidade indisponivel na despensa", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), ReceitasMain.class));
                                }
                            } else if (ing_uni.equalsIgnoreCase("mililitos") && produto.getUnidade().equalsIgnoreCase("litros")) {
                                if (ing_quant / 1000 <= produto.getQuantidade()) {
                                    produto.setQuantidade(produto.getQuantidade() - (ing_quant / 1000));
                                    dao_prod.editprodquant(produto, produto.getID());
                                    Toast.makeText(getActivity(), "Receita realizada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MenuPrincipal.class));
                                } else {
                                    Toast.makeText(getActivity(), "Quantidade indisponivel na despensa", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), ReceitasMain.class));
                                }
                            } else if (ing_uni.equalsIgnoreCase("litros") && produto.getUnidade().equalsIgnoreCase("mililitros")) {
                                if (ing_quant * 1000 <= produto.getQuantidade()) {
                                    produto.setQuantidade(produto.getQuantidade() - (ing_quant * 1000));
                                    dao_prod.editprodquant(produto, produto.getID());
                                    Toast.makeText(getActivity(), "Receita realizada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MenuPrincipal.class));
                                } else {
                                    Toast.makeText(getActivity(), "Quantidade indisponivel na despensa", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), ReceitasMain.class));
                                }
                            } else {
                                Toast.makeText(getActivity(), "As unidades digitadas na receita e despensa não compativeis", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), ReceitasMain.class));
                            }
                        }
                    }
                }

            }
        });


=======
>>>>>>> f2bc50f2b8db7d1a474b43432d398f43be9ca8c3
    }
}
