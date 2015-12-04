package com.webmyne.fridaynasta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView txtLogin;
    private MaterialEditText etEmail,etPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(PrefUtils.isLogin(getApplicationContext())){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        init();
    }



    private void init(){
        txtLogin = (TextView)findViewById(R.id.txtLogin);

        etEmail = (MaterialEditText)findViewById(R.id.etEmail);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Functions.isInternetConnected(LoginActivity.this)) {
                    processValidate();
                } else {
                    Toast.makeText(LoginActivity.this, "Please connect your Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void processValidate(){

        if(etEmail.getText().toString().length()==0){
            Toast.makeText(LoginActivity.this,"Please enter Email Address !!!",Toast.LENGTH_SHORT).show();
        }else if(!Functions.emailValidation(etEmail.getText().toString())){
            Toast.makeText(LoginActivity.this,"Please enter Valid Email Address !!!",Toast.LENGTH_SHORT).show();
        }else if(etPassword.getText().length()==0){
            Toast.makeText(LoginActivity.this,"Please enter Password !!!",Toast.LENGTH_SHORT).show();
        }else{
            processLogin();
        }
    }

    private void processLogin(){

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Checking login details...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Folks");
        query.whereEqualTo("email", etEmail.getText().toString().trim());
        query.whereEqualTo("password", etPassword.getText().toString().trim());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                progressDialog.dismiss();

                if (e == null) {
                    if (parseObjects.size() != 0) {

                        String email = parseObjects.get(0).getString("email");
                        String id = parseObjects.get(0).getString("id");
                        String name = parseObjects.get(0).getString("name");


                        PrefUtils.saveLogin(LoginActivity.this,true);

                        PrefUtils.saveUserID(LoginActivity.this,id);
                        PrefUtils.saveUserName(LoginActivity.this,name);
                        PrefUtils.saveUserEmail(LoginActivity.this,email);

                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        finish();
                    } else {

                        Toast.makeText(LoginActivity.this, "Login Failed. No user Found !!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Error to fetch details !!!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    //end of main class
}
