package batracorp.rateflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    private Catalog catalog;
    private TextView title,description;
    private Uri imageUri;
    private ImageView MovieImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catalog.getInstance();
        System.out.println("1");
        loadData();
       /* showMovie();*/


        findViewById(R.id.AddMovieButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMovieActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.NextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                catalog.next();
                showMovie();
            }
        });

    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Catalogo peliculas", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("Catalogo",null);
        Type type = new TypeToken<Catalog>() {}.getType();

        catalog = gson.fromJson(json,type);
    }
    private void    showMovie(){
        Movie currentMovie =catalog.getMovie();
        title =((TextView)findViewById(R.id.MovieTitle));
        title.setText(currentMovie.getTitle());
        description =((TextView)findViewById(R.id.MovieDescription));
        description.setText(currentMovie.getDescription());
        MovieImageView = (ImageView)findViewById(R.id.MovieImageView);
        imageUri =Uri.parse(currentMovie.getPicture()) ;
        MovieImageView.setImageURI(imageUri);

    }
}
