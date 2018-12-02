package project.tronku.qrry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEdit, passwordEdit, orgEdit, emailEdit, mobEdit;
    private Button signupButton;
    private String email, name, password, org, mob;
    private View layer, view;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        view = findViewById(android.R.id.content);
        nameEdit = findViewById(R.id.name);
        passwordEdit = findViewById(R.id.password);
        orgEdit = findViewById(R.id.organization);
        emailEdit = findViewById(R.id.emailid);
        mobEdit = findViewById(R.id.mobno);
        layer = findViewById(R.id.layer);
        loader = findViewById(R.id.loader);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEdit.getText().toString();
                org = orgEdit.getText().toString();
                password = passwordEdit.getText().toString();
                name = nameEdit.getText().toString();
                mob = mobEdit.getText().toString();

                if(password.length()==0 && email.length()==0 && name.length()==0 && org.length()==0 && mob.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter details!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(email.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Email-id!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Snackbar snackbar = Snackbar.make(view, "Enter correct email-id!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(password.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Password!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(name.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Name!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(orgEdit.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter organization!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(mob.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter Mobile number!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(!Patterns.PHONE.matcher(mob).matches()){
                    Snackbar snackbar = Snackbar.make(view, "Enter valid mobile number!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else {
                    emailEdit.setText("");
                    passwordEdit.setText("");
                    nameEdit.setText("");
                    orgEdit.setText("");
                    nameEdit.setEnabled(false);
                    emailEdit.setEnabled(false);
                    passwordEdit.setEnabled(false);
                    orgEdit.setEnabled(false);
                    layer.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.VISIBLE);
                    signUpData(name, email, org, password, mob);
                }
            }
        });
    }

    private void signUpData(final String name, final String email, final String org, String password, final String mob) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("email", email);
            object.put("organization", org);
            object.put("password", password);
            object.put("phone", mob);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, "", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        nameEdit.setEnabled(true);
                        emailEdit.setEnabled(true);
                        passwordEdit.setEnabled(true);
                        orgEdit.setEnabled(true);
                        layer.setVisibility(View.INVISIBLE);
                        loader.setVisibility(View.INVISIBLE);

                        Intent options = new Intent(SignUpActivity.this, OptionActivity.class);
                        options.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        options.putExtra("email", email);
                        options.putExtra("name", name);
                        options.putExtra("org", org);
                        startActivity(options);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("error",volleyError.toString());
                if(volleyError.networkResponse!=null && volleyError.networkResponse.statusCode==400){
                    String json = new String(volleyError.networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(json);
                        String error="";
                        if(response.has("email")){
                            JSONArray emailArray = response.getJSONArray("email");
                            String emailError = emailArray.get(0).toString().replaceAll("[\"]","");
                            error = error + emailError;
                        }
                        if(response.has("phone")){
                            JSONArray phoneArray = response.getJSONArray("phone");
                            String phoneError = phoneArray.get(0).toString().replaceAll("[\"]","");
                            error = error + "\n" + phoneError;
                        }
                        if(response.has("password")){
                            JSONArray passwordArray = response.getJSONArray("password");
                            String passwordError = passwordArray.get(0).toString().replaceAll("[\"]","");
                            error = error + "\n" + passwordError;
                        }

                        Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_SHORT);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        snackbar.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        objectRequest.setTag("auth");
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        queue.add(objectRequest);
    }
}
