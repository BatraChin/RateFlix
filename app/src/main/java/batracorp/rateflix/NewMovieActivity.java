package batracorp.rateflix;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewMovieActivity extends AppCompatActivity {


    private static final int PICK_IMAGE=100;
    private Uri imageUri;
    private ImageView imageView;
    private String Title;
    private String Description;
    private SharedPreferences sharedPreferences;
    private Catalog catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);


        Button mCreateButton = (Button) findViewById(R.id.CreateButton);
         imageView = (ImageView) findViewById(R.id.imageBox);



        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences Catalogo = getApplicationContext().getSharedPreferences("Catalogo", MODE_PRIVATE   );
                SharedPreferences.Editor editor = Catalogo.edit();
                Title = ((EditText)findViewById(R.id.titleBox)).getText().toString();
                Description = ((EditText) findViewById(R.id.descriptionBox)).getText().toString();
                if (!validInput())
                    Toast.makeText(NewMovieActivity.this, "Debes completar todos los campos.", Toast.LENGTH_SHORT).show();
                else {
                    saveMovie();


                    Intent intent = new Intent(NewMovieActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();

              //  Intent intent = new Intent(NewMovieActivity.this, MainActivity.class);
               // startActivity(intent);


            }
        });
    }
    private boolean    validInput(){
        if((imageUri!= null) && (Title.length()>0) && (Description.length()>10))
            return true;
        else
            return false;

    }
    private void    openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       // gallery.addCategory(Intent.CATEGORY_OPENABLE);
      //  gallery.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
       // gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(gallery,PICK_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void saveMovie(){
        catalog=catalog.getInstance();
        Bitmap bitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String Pictures = imageUri.toString();
        String Pictures = saveToInternalStorage(bitmap);


        catalog.addMovie(Title,Description,Pictures);


        sharedPreferences = getSharedPreferences("Catalogo peliculas",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(catalog);
        editor.putString("Catalogo",json);


        editor.apply();
    }


    private String saveToInternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = Title;
        File file = new File (myDir, fname);
        Log.d("saveToInternalStorage",file.getName()+"este es el nombre del archivo \n\n");

        String retorno =file.getAbsolutePath();
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("saveToInternalStorage","Guarde los datos en la memoria interna supuestamente \n\n\n");
        return retorno;
    }

    private String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

}
