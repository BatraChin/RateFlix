package batracorp.rateflix;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    protected SharedPreferences sharedPreferences ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences= getSharedPreferences("datos login",MODE_PRIVATE);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);



        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                String user = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
                String pass = ((EditText) findViewById(R.id.password)).getText().toString();
                ((AutoCompleteTextView) findViewById(R.id.email)).setText("");
                ((EditText) findViewById(R.id.password)).setText("");


                if (pass.length()<4){
                    Toast.makeText(LoginActivity.this, "La contrasena es muy corta.", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (Login(user, pass)){
                        //Si el login es exitoso, cambia de actividad.
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }





    private boolean Login(String user, String pass){


        if(userExists(user)){
           // Toast.makeText(LoginActivity.this, "El usuario existe.", Toast.LENGTH_SHORT).show();
            if(validPass(user, pass))
                    return true;
            else{
                Toast.makeText(LoginActivity.this, "Contrasena incorrecta.", Toast.LENGTH_SHORT).show();

                return false;

            }

        }
        else{
            Toast.makeText(LoginActivity.this, "El usuario no existe pero fue creado. Vuelva a intentarlo.", Toast.LENGTH_SHORT).show();

            register(user,pass);
            return false;
        }
    }
    private boolean userExists(String user){


        String json = sharedPreferences.getString(user,null);
        if (json == null) {
            return false;

        }
        else {
            return true;
        }
    }
    private boolean validPass(String user, String pass){

        String json = sharedPreferences.getString(user,null);


        if (json.equals(pass))
            return true;
        else
            return false;

    }
    private void register(String user, String pwd){


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(user,pwd);
        editor.apply();
    }



}

