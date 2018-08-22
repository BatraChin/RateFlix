package batracorp.rateflix;

import android.app.ActionBar;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;

public class InvitedActivity extends AppCompatActivity {

    private Catalog catalog;
    private TextView title, description;
    private RatingBar   rating;
    private Uri imageUri;
    private ImageView MovieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited);
        catalog=catalog.getInstance();

        if(existsData()){
            loadData();
            showMovie();
            MovieImageView= findViewById(R.id.MovieImageView);
        }
        else{
            title =findViewById(R.id.MovieTitle);
            title.setText("NO HAY PELICULAS EN EL CATALOGO");
            description= findViewById(R.id.MovieDescription);
            description.setText("Los administradores aun no han cargado peliculas. Debe esperar a que las mismas sean cargadas.");

            MovieImageView= findViewById(R.id.MovieImageView);
            MovieImageView.setVisibility(View.INVISIBLE);

        }



        findViewById(R.id.NextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(catalog.next()) {
                    showMovie();
                }
            }
        });
        findViewById(R.id.PreviousButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(catalog.previous()) {
                    showMovie();
                }
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

    private void showMovie() {
        Movie currentMovie = catalog.getMovie();
        title = ((TextView) findViewById(R.id.MovieTitle));
        title.setText(currentMovie.getTitle());
        description = ((TextView) findViewById(R.id.MovieDescription));
        description.setText(currentMovie.getDescription());

        rating =(RatingBar) findViewById(R.id.ratingBar);
        rating.setRating(currentMovie.getRating());
        loadImageFromStorage(currentMovie.getPicture());

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/netflixfont.ttf");
        title.setTypeface(custom_font);
        description.setTypeface(custom_font);

    }


    private void loadImageFromStorage(String path) {

        try {

            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView) findViewById(R.id.MovieImageView);
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
