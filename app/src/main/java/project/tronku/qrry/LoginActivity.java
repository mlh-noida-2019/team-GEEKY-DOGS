package project.tronku.qrry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, signup;
    private View view;
    private String emailid, passwordText;
    private View layer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        view = findViewById(android.R.id.content);

        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        layer = findViewById(R.id.layer);
        progressBar = findViewById(R.id.loader);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailid = email.getText().toString();
                passwordText = password.getText().toString();

                if(passwordText.length()==0 && emailid.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter details!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(emailid.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Email-id or Mobile no!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(passwordText.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Password!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else {
                    email.setText("");
                    password.setText("");
                    email.setEnabled(false);
                    password.setEnabled(false);
                    layer.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    //loginUser(emailid, passwordText);
                    startActivity(new Intent(LoginActivity.this, OptionActivity.class));
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        RequestQueue login;
        JSONObject credentials = new JSONObject();
        try{
            credentials.put("email", email);
            credentials.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest loginreq = new JsonObjectRequest(Request.Method.POST, "", credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse!=null && error.networkResponse.statusCode==404){
                    String json = new String(error.networkResponse.data);
                    try {
                        JSONObject jsonError = new JSONObject(json);

                        if(jsonError.has("error")){
                            String errorString = jsonError.get("error").toString();
                            Snackbar snackbar = Snackbar.make(view, errorString, Snackbar.LENGTH_SHORT);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            snackbar.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        login = Volley.newRequestQueue(LoginActivity.this);
        login.add(loginreq);
    }
}
