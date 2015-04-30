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
import java.util.Date;


public class ProdutoInsert extends ActionBarActivity {

    private ProdutosDao dao = ProdutosDao.newInstance(this);
    private String image_uri = "";
    private Button insertimage;
    private ImageView photoContainer;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String APP_NAME = "HomeMarket";
    private static final String LOGTAG = ProdutoInsert.class.getSimpleName();
    private static final int CAPTURE_IMAGE_REQCODE = 200;
    private static final int PICK_IMAGE_CODE = 100;
    private Uri fileUri;
    private Spinner units;
    private CheckBox insertcheck;
    private EditText consumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        units = (Spinner )findViewById(R.id.insert_unit);
        setContentView(R.layout.activity_produto_insert);


        Handled();
        addListenerOnSpinnerItemSelection();
    }

    public void addListenerOnSpinnerItemSelection() {
        units = (Spinner) findViewById(R.id.insert_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unitis_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(adapter);
    }

    private void Handled() {
        insertimage = (Button )findViewById(R.id.insert_produto_imagem);
        photoContainer = (ImageView )findViewById(R.id.insert_thumbs);
        insertcheck = (CheckBox )findViewById(R.id.insert_produto_check);
        consumo = (EditText )findViewById(R.id.insert_produto_consumo);
        insertcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(insertcheck.isChecked()){
                    consumo.setVisibility(View.VISIBLE);
                }
                else{
                    consumo.setVisibility(View.INVISIBLE);
                }
            }
        });

        insertimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View menuItemView = findViewById(R.id.insert_produto_imagem);
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
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                photoContainer.setVisibility(View.VISIBLE);
                photoContainer.setImageBitmap(BitmapFactory
                        .decodeFile(image_uri));

            }
        }else if (requestCode == CAPTURE_IMAGE_REQCODE) {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, getString(R.string.photo_capture_success),
                            Toast.LENGTH_SHORT).show();

                    Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    image_uri = fileUri.getPath();
                    photoContainer.setVisibility(View.VISIBLE);
                    photoContainer.setImageBitmap(bitmap);
                    photoContainer.setAdjustViewBounds(true);
                }


        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.image_pick_abort),
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.photo_capture_error),
                    Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produto_insert, menu);
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
        if(id == R.id.action_produto_insert){
            insert();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        units = (Spinner )findViewById(R.id.insert_unit);
        String nome = ((EditText ) findViewById(R.id.insert_produto_nome)).getText().toString();
        String quantidade = ((EditText )findViewById(R.id.insert_produto_quantidade)).getText().toString();
        String preço = ((EditText )findViewById(R.id.insert_produto_preço)).getText().toString();
        String consumo = ((EditText )findViewById(R.id.insert_produto_consumo)).getText().toString();
        String unidade = String.valueOf(units.getSelectedItem());
        boolean check = ((CheckBox )findViewById(R.id.insert_produto_check)).isChecked();
        boolean error = false;
        if(nome.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.insert_produto_nome)).setError("Campo Obrigatorio");
            error = true;
        }
        if(quantidade.equalsIgnoreCase("")){
            quantidade = "0";
        }
        if(preço.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.insert_produto_preço)).setError("Campo Obrigatorio");
            error = true;
        }
        if(check && consumo.equalsIgnoreCase("")){
            ((EditText) findViewById(R.id.insert_produto_consumo)).setError("Campo Obrigatorio");
            error = true;
        }
        Produtos produto = new Produtos();
        if(error == false){
        produto.setNome(nome);
        produto.setQuantidade(Double.parseDouble(quantidade));
        produto.setPreço(Double.parseDouble(preço));
        if(check){
            produto.setConsumo(Double.parseDouble(consumo));}
        produto.setChecked(check);
        produto.setUnidade(unidade);
        produto.setImagem(image_uri);

        dao.add(produto);
        Toast.makeText(this,"Produto Adicionado",Toast.LENGTH_SHORT).show();
        startActivity((new Intent(this,Produto.class)));}

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