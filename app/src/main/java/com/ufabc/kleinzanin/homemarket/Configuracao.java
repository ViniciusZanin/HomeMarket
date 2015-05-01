package com.ufabc.kleinzanin.homemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Ingredientes;
import com.ufabc.kleinzanin.homemarket.model.IngredientesDAO;
import com.ufabc.kleinzanin.homemarket.model.MercadoDAO;
import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;
import com.ufabc.kleinzanin.homemarket.model.ReceitasDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class Configuracao extends ActionBarActivity {
    private static final String LOGTAG = Configuracao.class.getSimpleName();
    private TextView limpar_app;
    private TextView backup_app;
    private TextView restore_backup;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        init();
    }

    private void init() {
        limpar_app = (TextView )findViewById(R.id.limp_app);
        final Intent intent = new Intent(this, ReceitasMain.class);
        limpar_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracao.this);
                builder.setMessage("Deseja limpar a aplicação?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Produtos> produtos;
                        ProdutosDao dao_prod = ProdutosDao.newInstance(Configuracao.this);
                        produtos = dao_prod.list();
                        for(int i = 0; i<produtos.size(); i++){
                            Produtos produto = produtos.get(i);
                            if(produto.getImagem() != null){
                                File file = new File(produto.getImagem());
                                file.delete();
                            }
                        }
                        dao_prod.dropTable();
                        IngredientesDAO dao_ing = IngredientesDAO.newInstance(Configuracao.this);
                        dao_ing.dropTable();
                        MercadoDAO dao_merc = MercadoDAO.newInstance(Configuracao.this);
                        dao_merc.dropTable();
                        ReceitasDAO dao_rec = ReceitasDAO.newInstance(Configuracao.this);
                        dao_rec.dropTable();
                        startActivity(new Intent(Configuracao.this, MenuPrincipal.class));

                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();
            }
        });

        backup_app = (TextView )findViewById(R.id.backup_app);
        backup_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbPath = "/data/data/com.ufabc.kleinzanin.homemarket/databases/HomeMarket.db";
                File dbBackup = new File(dbPath);
                try {
                    FileInputStream fis = new FileInputStream(dbBackup);
                    String outFileName = Environment.getExternalStorageDirectory()+"/HomeMarket.db";
                    OutputStream output = new FileOutputStream(outFileName);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer))>0){
                        output.write(buffer, 0, length);
                    }
                    output.flush();
                    output.close();
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Backup salvo no cartão SD com o nome de HomeMarket.db", Toast.LENGTH_SHORT).show();
            }
        });

        restore_backup = (TextView )findViewById(R.id.restore_backup);
        restore_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracao.this);
                builder.setMessage("Para restaurar o backup coloque o arquivo HomeMarket.db no seu cartão SD\n" +
                        "Os arquivos atuais serão perdidos, deseja restaurar o backup?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*try {

                                    File sd = Environment.getExternalStorageDirectory();
                                    File data = Environment.getDataDirectory();

                                    if (sd.canWrite()) {
                                        String currentDBPath = "/data/com.ufabc.kleinzanin.homemarket/databases/HomeMarket.db";
                                        String backupDBPath = "HomeMarket.db";
                                        File currentDB = new File(data, currentDBPath);
                                        File backupDB = new File(sd, backupDBPath);

                                        if (currentDB.exists()) {
                                            currentDB.delete();
                                            FileChannel src = new FileInputStream(backupDB).getChannel();
                                            FileChannel dst = new FileOutputStream(currentDB).getChannel();
                                            dst.transferFrom(src, 0, src.size());
                                            src.close();
                                            dst.close();
                                            Toast.makeText(getApplicationContext(), "Backup restaurado com sucesso", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e(LOGTAG, "Não achou a DB");}
                                    }
                                } catch (Exception e) {
                                }*/
                            }
                            
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
