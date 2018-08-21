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
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

public class NewMovieActivity extends AppCompatActivity {


    private static final int PICK_IMAGE=100;
    private Uri imageUri;
    private ImageView imageView;
    private String Title;
    private String Description;
    private float Rating;
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

               // SharedPreferences Catalogo = getApplicationContext().getSharedPreferences("Catalogo", MODE_PRIVATE   );
                //SharedPreferences.Editor editor = Catalogo.edit();
                Title = ((EditText)findViewById(R.id.titleBox)).getText().toString();
                Description = ((EditText) findViewById(R.id.descriptionBox)).getText().toString();
                Rating = ((RatingBar)findViewById(R.id.ratingBar)).getRating();
                catalog = catalog.getInstance();
                if (!validInput())
                    Toast.makeText(NewMovieActivity.this, "Debes completar todos los campos.", Toast.LENGTH_SHORT).show();
                else {
                    if(existsData())
                        loadData();
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


            }
        });
    }
    private boolean existsData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Catalogo peliculas",MODE_PRIVATE);
        String json = sharedPreferences.getString("Catalogo", null);
        return json!=null;

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Catalogo peliculas", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("Catalogo", null);
        Type type = new TypeToken<Catalog>() {
        }.getType();

        catalog = gson.fromJson(json, type);
    }
    private boolean    validInput(){
        if((imageUri!= null) && (Title.length()>0) && (Description.length()>10))
            return true;
        else
            return false;

    }
    private void    openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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



        Bitmap bitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String Pictures = saveToInternalStorage(bitmap);


        catalog.addMovie(Title,Description,Rating,Pictures);


        sharedPreferences = getSharedPreferences("Catalogo peliculas",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(catalog);
        editor.putString("Catalogo",json);

        editor.apply();

    }


    /**
     *
     * @param bitmapImage to store
     * @return returns the String URL for the image given by param
     */
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, Title);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/"+Title;
    }

}
