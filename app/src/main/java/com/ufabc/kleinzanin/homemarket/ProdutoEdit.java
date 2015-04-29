package com.ufabc.kleinzanin.homemarket;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufabc.kleinzanin.homemarket.model.Produtos;
import com.ufabc.kleinzanin.homemarket.model.ProdutosDao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProdutoEdit extends ActionBarActivity {

    private EditText nome;
    private EditText quantidade;
    private EditText preço;
    private EditText consumo;
    private ImageView produtoimage;
    private CheckBox produtoCheck;
    private Spinner units;
    private Button editimagem;
    private String image_uri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String APP_NAME = "HomeMarket";
    private static final String LOGTAG = ProdutoInsert.class.getSimpleName();
    private static final int CAPTURE_IMAGE_REQCODE = 200;
    private static final int PICK_IMAGE_CODE = 100;
    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_edit);
        init();
        Handled();
    }

    private void Handled() {
        editimagem = (Button )findViewById(R.id.edit_imagem);
        produtoimage = (ImageView )findViewById(R.id.edit_produto_imagem);
        produtoCheck = (CheckBox )findViewById(R.id.edit_produto_check);
        consumo = (EditText )findViewById(R.id.edit_produto_consumo);
        produtoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(produtoCheck.isChecked()){
                    consumo.setVisibility(View.VISIBLE);
                }
                else{
                    consumo.setVisibility(View.INVISIBLE);
                }
            }
        });

        editimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View menuItemView = findViewById(R.id.edit_imagem);
                PopupMenu popup = new PopupMenu(getApplicationContext(), menuItemView);
                popup.getMenuInflater().inflate(R.menu.popup_menu_insert_imagem, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.one){

                            Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            if(fileUri != null){
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // file output
                                startActivityForResult(intent, CAPTURE_IMAGE_REQCODE);
                            } else
                                Toast.makeText(getApplicationContext(), getString(R.string.storage_error),
                                        Toast.LENGTH_SHORT).show();

                        }
                        if(id == R.id.two){
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            if(fileUri != null){
                                // intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
                                startActivityForResult(intent, PICK_IMAGE_CODE);
                            } else
                                Toast.makeText(getApplicationContext(), getString(R.string.storage_error),
                                        Toast.LENGTH_SHORT).show();
                        }
                        return  true;
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<Produtos> produtos;
                ProdutosDao dao = ProdutosDao.newInstance(this);
                produtos = dao.list();
                int pos = getIntent().getExtras().getInt("produtoPosition");
                Produtos produto = produtos.get(pos);
                if(produto.getImagem() != null) {
                    File image = new File(produto.getImagem());
                    if(image.exists()) {
                        image.delete();
                        produto.setImagem(null);
                        Log.e(LOGTAG,String.valueOf(image) + "deletada");
                    }
                }
                Toast.makeText(this, getString(R.string.photo_capture_success),
                        Toast.LENGTH_SHORT).show();
                fileUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                image_uri = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                produtoimage.setImageBitmap(BitmapFactory
                        .decodeFile(image_uri));

            }
        }else if (requestCode == CAPTURE_IMAGE_REQCODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<Produtos> produtos;
                ProdutosDao dao = ProdutosDao.newInstance(this);
                produtos = dao.list();
                int pos = getIntent().getExtras().getInt("produtoPosition");
                Produtos produto = produtos.get(pos);
                if(produto.getImagem() != null) {
                    File image = new File(produto.getImagem());
                    if(image.exists()) {
                        image.delete();
                        produto.setImagem(null);
                        Log.e(LOGTAG,String.valueOf(image) + "deletada");
                    }
                }
                Toast.makeText(this, getString(R.string.photo_capture_success),
                        Toast.LENGTH_SHORT).show();

                Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                image_uri = fileUri.getPath();
                produtoimage.setImageBitmap(bitmap);
                produtoimage.setAdjustViewBounds(true);
            }


        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.image_pick_abort),
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.photo_capture_error),
                    Toast.LENGTH_SHORT).show();
        }


    }


    private void init() {
        units = (Spinner) findViewById(R.id.edit_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unitis_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(adapter);
        ArrayList<Produtos> produtos;
        ProdutosDao dao = ProdutosDao.newInstance(this);
        produtos = dao.list();
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Produtos produto = produtos.get(pos);
        nome = (EditText) findViewById(R.id.edit_produto_nome);
        quantidade = (EditText ) findViewById(R.id.edit_produto_quantidade);
        preço = (EditText ) findViewById(R.id.edit_produto_preço);
        consumo = (EditText ) findViewById(R.id.edit_produto_consumo);
        produtoimage = (ImageView ) findViewById(R.id.edit_produto_imagem);
        produtoCheck = (CheckBox) findViewById(R.id.edit_produto_check);
        nome.setText(produto.getNome());
        quantidade.setText(Integer.toString(produto.getQuantidade()));
        preço.setText(produto.getPreço());
        image_uri = produto.getImagem();
        Bitmap bitmap = BitmapFactory.decodeFile(image_uri);
        produtoimage.setImageBitmap(bitmap);
        produtoCheck.setChecked(produto.getChecked());
        if(produtoCheck.isChecked()){
            consumo.setVisibility(View.VISIBLE);
            consumo.setText(Integer.toString(produto.getConsumo()));
        }
        int selectionPosition= adapter.getPosition(produto.getUnidade());
        units.setSelection(selectionPosition);

    }

    private void editProduto(){
        ArrayList<Produtos> produtos;
        ProdutosDao dao = ProdutosDao.newInstance(this);
        produtos = dao.list();
        int pos = getIntent().getExtras().getInt("produtoPosition");
        Produtos produto = produtos.get(pos);
        String nnome = ((EditText ) findViewById(R.id.edit_produto_nome)).getText().toString();
        String nquantidade = ((EditText )findViewById(R.id.edit_produto_quantidade)).getText().toString();
        String npreço = ((EditText )findViewById(R.id.edit_produto_preço)).getText().toString();
        String unidade = String.valueOf(units.getSelectedItem());
        String nconsumo = ((EditText )findViewById(R.id.edit_produto_consumo)).getText().toString();
        boolean ncheck = ((CheckBox )findViewById(R.id.edit_produto_check)).isChecked();
        boolean error = false;
        if(nnome.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.edit_produto_nome)).setError("Campo Obrigatorio");
            error = true;
        }
        if(nquantidade.equalsIgnoreCase("")){
            nquantidade = "0";
        }
        if(npreço.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.edit_produto_preço)).setError("Campo Obrigatorio");
            error = true;
        }
        if(ncheck && nconsumo.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.edit_produto_consumo)).setError("Campo Obrigatorio");
            error = true;
        }
        if(error == false) {
            produto.setNome(nnome);
            produto.setQuantidade(Integer.parseInt(nquantidade));
            produto.setPreço(npreço);
            produto.setUnidade(unidade);
            if (image_uri != null) {
                produto.setImagem(image_uri);
            }
            if (ncheck) {
                produto.setConsumo(Integer.parseInt(nconsumo));
            }
            produto.setChecked(ncheck);
            dao.edit(produto, produto.getID());
            Toast.makeText(this, "Produto Editado", Toast.LENGTH_SHORT).show();
            startActivity((new Intent(this, Produto.class)));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produto_edit, menu);
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
        if(id == R.id.action_edit_produto){
            editProduto();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a file Uri for saving an image or video
     *
     * @author Google
     * */
    private Uri getOutputMediaFileUri(int type){
        File file = getOutputMediaFile(type);

        if (file != null && isExternalStorageWritable())
            return Uri.fromFile(file);
        else {
            Log.e(LOGTAG, "Failed to get access to external storage");

            return null;
        }
    }

    /**
     * Create a File for saving an image or video
     *
     * @author Google
     * */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), APP_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(LOGTAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}

