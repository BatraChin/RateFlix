package batracorp.rateflix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddMovieButton.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewMovieActivity.class);
                startActivity(intent);
            }
        });
    }
}
