package com.example.myapplication.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.Connexion;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Button ButtonLogin;
    public Intent MainIntent;
    public String USERNAME;
    public  String PASSWORD;
    String UrlProfileInfo;
    private Connexion profileAdapter;
    private Profile profilelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainIntent=new Intent(this, MainActivity.class);
        ButtonLogin=findViewById(R.id.cirLoginButton);
        ButtonLogin.setOnClickListener(new Button.OnClickListener()  {

            @Override
            public void onClick(View view) {
                USERNAME=((TextView)(findViewById(R.id.Email))).getText().toString();
                PASSWORD=((TextView)(findViewById(R.id.Password))).getText().toString();
                if(USERNAME.length()!=0 && PASSWORD.length()!=0)
                {
                    try {
                        String result;
                        UrlProfileInfo="http://digitalisi.tn:8080/engine-rest/user/"+USERNAME+"/profile";
                        profileAdapter=new Connexion();
                        System.out.println("Connexion : "+USERNAME);
                        System.out.println("url Connexion : "+UrlProfileInfo);
                        result = profileAdapter.getData(UrlProfileInfo,USERNAME,PASSWORD);
                        Gson g = new Gson();
                       if(!result.equals("No Data"))
                        {
                            profilelog = g.fromJson(result, Profile.class);
                            MainIntent.putExtra("ProfileLog", profilelog);
                            MainIntent.putExtra("username", USERNAME);
                            MainIntent.putExtra("password", PASSWORD);
                            startActivity(MainIntent);
                        }
                        else {
                            // Username or password false
                            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());
                            dlgAlert.setMessage("wrong password or username");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());
                    dlgAlert.setMessage("Please Enter Username And Password !");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }



            }

        });
    }

}
