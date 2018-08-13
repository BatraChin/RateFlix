package batracorp.rateflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences Catalogo = getApplicationContext().getSharedPreferences("Catalogo", MODE_PRIVATE   );
        SharedPreferences.Editor editor = settings.edit();

        // Button mAddMovieButton = (Button) findViewById(R.id.AddMovieButton);
        findViewById(R.id.AddMovieButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMovieActivity.class);
                startActivity(intent);
            }
        });

    }
}
