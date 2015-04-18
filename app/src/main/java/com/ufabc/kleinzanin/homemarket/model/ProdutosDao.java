package com.ufabc.kleinzanin.homemarket.model;

/**
 * Created by Vinicius on 16/04/2015.
 */
import com.ufabc.kleinzanin.homemarket.R;

import java.util.ArrayList;
import java.util.List;

public class ProdutosDao {
    private static ProdutosDao dao;
    private List<Produtos> produtos;

    protected ProdutosDao() {}

    private void init() {
        produtos = new ArrayList<>();
        // TODO: remove when "insert" operation is implemented in the app
        loadTestData();
    }

    private void loadTestData() {
        Produtos c;

        c = new Produtos();

        c.setNome("Manga");
        c.setPreço("4.99");
        c.setQuantidade(200);
        c.setConsumo(1000);
        c.setChecked(true);
        c.setImagem(R.drawable.manga);
        c.setUnidade("g");
        produtos.add(c);

    }

    public static ProdutosDao newInstance() {
        if (dao == null) {
            dao = new ProdutosDao();
            dao.init();
        }

        return dao;
    }

    public void add(Produtos produto) {
        produtos.add(produto);
    }
    public void edit(Produtos produto){ produtos.remove(produto);produtos.add(produto);}

    public boolean remove(int position) {
        boolean res = true;

        try {
            produtos.remove(position);
        } catch(IndexOutOfBoundsException e) {
            res = false;
        }

        return res;
    }

    public Produtos[] list(Produtos produto) {
        return produtos.toArray(new Produtos[produtos.size()]);
    }

    public int size() {
        return produtos.size();
    }

    public Produtos getItemAt(int pos) {
        return produtos.get(pos);
    }


}