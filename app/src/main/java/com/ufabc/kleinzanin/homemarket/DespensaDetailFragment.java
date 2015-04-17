        package com.ufabc.kleinzanin.homemarket;

        import android.os.Bundle;
        import android.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.ufabc.kleinzanin.homemarket.model.Produtos;

        import org.w3c.dom.Text;



        /**
         * A simple {@link Fragment} subclass.
         */
        public class DespensaDetailFragment extends Fragment {

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
                return inflater.inflate(R.layout.fragment_despensa_detail, container, false);
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
                quantidade.setText(Integer.toString(produto.getQuantidade()));
                preço.setText(produto.getPreço());
                produtoimage.setImageResource(produto.getImagem());
                produtoCheck.setChecked(produto.getChecked());
                if(produto.getChecked() == true){
                    consumo.setText(Integer.toString(produto.getConsumo()));
                    consumo.setVisibility(View.VISIBLE);
                }
            }


        }
