package batracorp.rateflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

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
         Title = ((EditText)findViewById(R.id.titleBox)).getText().toString();
         Description = ((EditText) findViewById(R.id.descriptionBox)).getText().toString();


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences Catalogo = getApplicationContext().getSharedPreferences("Catalogo", MODE_PRIVATE   );
                SharedPreferences.Editor editor = Catalogo.edit();
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


        String Pictures = imageUri.toString();


        catalog.addMovie(Title,Description,Pictures);


        sharedPreferences = getSharedPreferences("Catalogo peliculas",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(catalog);
        editor.putString("Catalogo",json);


        editor.apply();
    }

}
