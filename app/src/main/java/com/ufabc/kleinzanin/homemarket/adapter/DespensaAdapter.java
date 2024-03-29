package com.ufabc.kleinzanin.homemarket.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.Despensa;
import com.ufabc.kleinzanin.homemarket.Produto;
import com.ufabc.kleinzanin.homemarket.R;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Vinicius on 29/04/2015.
 */
public class DespensaAdapter extends BaseAdapter {
    private ProdutosDao dao;
    private Context context;
    LayoutInflater inflater;
    ArrayList<Produtos> produtos;

    public  DespensaAdapter(Context c){
        this.context = c;
        this.dao = ProdutosDao.newInstance(c);
        inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        produtos = dao.despensa();
    }


    public boolean remove(int position){
        boolean status = dao.remove(position);
        if (status)
            this.notifyDataSetChanged();

        return status;
    }

    public void edit(Produtos produto, int id){
        dao.edit(produto,id);
        this.notifyDataSetChanged();
    }

    public void add(Produtos produto){
        dao.add(produto);
        this.notifyDataSetChanged();
    }

    public Produtos getItemAt(int position) { return produtos.get(position);}


    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Produtos produto = produtos.get(position);
        TextView nome = null;
        TextView quantidade = null;
        ImageButton add;
        ImageButton remove;
        Spinner units;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.despensa_list_item,null);
        }
        add = (ImageButton)convertView.findViewById(R.id.add_quanti_despensa);
        remove = (ImageButton )convertView.findViewById(R.id.remove_quanti_despensa);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflal = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View promptsView = inflater.inflate(R.layout.dialog_despensa, null);
                builder.setView(promptsView);
                builder.setPositiveButton(R.string.prod_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog ) dialog;
                        EditText quantidade = (EditText)f.findViewById(R.id.produto_quantidade);
                        Spinner units = (Spinner)f.findViewById(R.id.spinner_dialog);
                        String unidade = String.valueOf(units.getSelectedItem());
                        boolean error = false;
                        double quant = Double.parseDouble(quantidade.getText().toString());
                        if(quant != 0 || String.valueOf(quant) != null) {
                            if (unidade.equalsIgnoreCase("kilogramas") || unidade.equalsIgnoreCase("gramas")) {
                                if (produto.getUnidade().equalsIgnoreCase("litros") || produto.getUnidade().equalsIgnoreCase("mililitros") ||
                                        produto.getUnidade().equalsIgnoreCase("unidades")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {
                                    if (produto.getUnidade().equalsIgnoreCase(unidade)) {
                                        produto.setQuantidade(quant + produto.getQuantidade());
                                        edit(produto, produto.getID());

                                    } else if (produto.getUnidade().equalsIgnoreCase("gramas") && unidade.equalsIgnoreCase("kilogramas")) {
                                        produto.setQuantidade((quant * 1000) + produto.getQuantidade());
                                        edit(produto, produto.getID());
                                    } else{
                                        produto.setQuantidade((quant / 1000) + produto.getQuantidade());
                                        edit(produto, produto.getID());
                                    }
                                }
                            }
                            if (unidade.equalsIgnoreCase("litros") || unidade.equalsIgnoreCase("mililitros")) {
                                if (produto.getUnidade().equalsIgnoreCase("kilogramas") || produto.getUnidade().equalsIgnoreCase("gramas") ||
                                        produto.getUnidade().equalsIgnoreCase("unidades")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {
                                    if (produto.getUnidade().equalsIgnoreCase(unidade)) {
                                        produto.setQuantidade(quant + produto.getQuantidade());
                                        edit(produto, produto.getID());

                                    } else if (produto.getUnidade().equalsIgnoreCase("Mililitros") && unidade.equalsIgnoreCase("litros")) {
                                        produto.setQuantidade((quant * 1000) + produto.getQuantidade());
                                        edit(produto, produto.getID());
                                    } else{
                                        produto.setQuantidade((quant / 1000) + produto.getQuantidade());
                                        edit(produto, produto.getID());

                                    }
                                }
                            }
                            if (unidade.equalsIgnoreCase("unidades")) {
                                if (produto.getUnidade().equalsIgnoreCase("kilogramas") || produto.getUnidade().equalsIgnoreCase("gramas") ||
                                        produto.getUnidade().equalsIgnoreCase("litros") || produto.getUnidade().equalsIgnoreCase("mililitros")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {
                                    if (produto.getUnidade().equalsIgnoreCase(unidade)) {
                                        produto.setQuantidade(quant + produto.getQuantidade());
                                        edit(produto, produto.getID());
                                    }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton(R.string.prodcancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                Spinner units = (Spinner) promptsView.findViewById(R.id.spinner_dialog);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                            R.array.unitis_array, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    units.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflal = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View promptsView = inflater.inflate(R.layout.dialog_despensa, null);
                builder.setView(promptsView);
                builder.setPositiveButton(R.string.prod_remo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog ) dialog;
                        EditText quantidade = (EditText)f.findViewById(R.id.produto_quantidade);
                        Spinner units = (Spinner)f.findViewById(R.id.spinner_dialog);
                        String unidade = String.valueOf(units.getSelectedItem());
                        boolean error = false;
                        double quant = Double.parseDouble(quantidade.getText().toString());
                        if(quant != 0 || String.valueOf(quant) != null) {
                            if (unidade.equalsIgnoreCase("kilogramas") || unidade.equalsIgnoreCase("gramas")) {
                                if (produto.getUnidade().equalsIgnoreCase("litros") || produto.getUnidade().equalsIgnoreCase("mililitros") ||
                                        produto.getUnidade().equalsIgnoreCase("unidades")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {

                                    if (produto.getUnidade().equalsIgnoreCase(unidade)){
                                        if( (produto.getQuantidade()-quant) >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - quant);
                                            edit(produto, produto.getID());
                                        } else{Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show();}

                                    } else if (produto.getUnidade().equalsIgnoreCase("gramas") && unidade.equalsIgnoreCase("kilogramas")) {
                                        if((produto.getQuantidade()-(quant*1000)) >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - (quant * 1000));
                                            edit(produto, produto.getID());
                                        } else {Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show(); }
                                    } else{
                                        if(produto.getQuantidade() - (quant/1000) >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - (quant/1000));
                                            edit(produto, produto.getID());
                                        } else{Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show(); }
                                    }
                                }
                            }
                            if (unidade.equalsIgnoreCase("litros") || unidade.equalsIgnoreCase("mililitros")) {
                                if (produto.getUnidade().equalsIgnoreCase("kilogramas") || produto.getUnidade().equalsIgnoreCase("gramas") ||
                                        produto.getUnidade().equalsIgnoreCase("unidades")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {
                                    if (produto.getUnidade().equalsIgnoreCase(unidade)) {
                                        if(produto.getQuantidade() - quant >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - quant);
                                            edit(produto, produto.getID());
                                        } else {Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show(); }

                                    } else if (produto.getUnidade().equalsIgnoreCase("Mililitros") && unidade.equalsIgnoreCase("litros")) {
                                        if(produto.getQuantidade() - (quant*1000) >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - (quant*1000));
                                            edit(produto, produto.getID());
                                        } else {Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show(); }
                                    } else{
                                        if(produto.getQuantidade() - (quant/1000) >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - (quant/1000));
                                            edit(produto, produto.getID());
                                        } else{Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show(); }

                                    }
                                }
                            }
                            if (unidade.equalsIgnoreCase("unidades")) {
                                if (produto.getUnidade().equalsIgnoreCase("kilogramas") || produto.getUnidade().equalsIgnoreCase("gramas") ||
                                        produto.getUnidade().equalsIgnoreCase("litros") || produto.getUnidade().equalsIgnoreCase("mililitros")) {
                                    error = true;
                                    Toast.makeText(context, "Unidade errada", Toast.LENGTH_LONG).show();
                                }
                                if (!error) {
                                    if (produto.getUnidade().equalsIgnoreCase(unidade)) {
                                        if(produto.getQuantidade() - quant >= 0) {
                                            produto.setQuantidade(produto.getQuantidade() - quant);
                                            edit(produto, produto.getID());
                                        } else { Toast.makeText(context, "Quantidade Invalida", Toast.LENGTH_LONG).show();}
                                    }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton(R.string.prodcancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                Spinner units = (Spinner) promptsView.findViewById(R.id.spinner_dialog);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                        R.array.unitis_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                units.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        nome = (TextView )convertView.findViewById(R.id.produto_nome);
        quantidade = (TextView ) convertView.findViewById(R.id.produto_quantidade);
        nome.setText(produto.getNome());
        if(produto.getChecked() == true){
            quantidade.setText(Double.toString(produto.getQuantidade()) + "/" +
                    Double.toString(produto.getConsumo()) + " " + produto.getUnidade());}
        else{
            quantidade.setText(Double.toString(produto.getQuantidade()) + " " + produto.getUnidade());

        }
        return convertView;
    }
}
