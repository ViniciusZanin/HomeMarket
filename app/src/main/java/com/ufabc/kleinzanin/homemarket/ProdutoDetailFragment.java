        package com.ufabc.kleinzanin.homemarket;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.ufabc.kleinzanin.homemarket.model.Produtos;


        /**
         * A simple {@link Fragment} subclass.
         */
        public class ProdutoDetailFragment extends Fragment {

            private TextView nome;
            private TextView quantidade;
            private TextView preço;
            private TextView consumo;
            private ImageView produtoimage;
            private CheckBox produtoCheck;



            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_produto_detail, container, false);
            }

            private void init() {
                nome = (TextView )getView().findViewById(R.id.produto_nome);
                quantidade = (TextView )getView().findViewById(R.id.produto_quantidade);
                preço = (TextView )getView().findViewById(R.id.produto_preço);
                consumo = (TextView )getView().findViewById(R.id.produto_consumo);
               produtoimage = (ImageView ) getView().findViewById(R.id.produto_imagem);
                produtoCheck = (CheckBox ) getView().findViewById(R.id.produto_check);

            }

            public void showProdutos(Produtos produto) {
                init();

                nome.setText(produto.getNome());
                quantidade.setText(Double.toString(produto.getQuantidade())+" " + produto.getUnidade());
                preço.setText(Double.toString(produto.getPreço()));
                if(produto.getImagem() != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(produto.getImagem());
                    produtoimage.setImageBitmap(bitmap);
                }
                produtoCheck.setChecked(produto.getChecked());
                if(produto.getChecked()){
                    consumo.setText(Double.toString(produto.getConsumo())+" " + produto.getUnidade());
                }
            }


        }
