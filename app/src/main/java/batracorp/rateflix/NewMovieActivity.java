package batracorp.rateflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);

        Button mCreateButton = (Button) findViewById(R.id.CreateButton);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences Catalogo = getApplicationContext().getSharedPreferences("Catalogo", MODE_PRIVATE   );
                SharedPreferences.Editor editor = Catalogo.edit();
                //Catalog

                Intent intent = new Intent(NewMovieActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });

    }
}
