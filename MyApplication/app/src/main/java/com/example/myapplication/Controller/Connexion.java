package com.example.myapplication.Controller;

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connexion {

    OkHttpClient client = new OkHttpClient();

    public String getData(String url,String username,String password) throws IOException
    {

        client.newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", Credentials.basic(username, password))
                .build();

        Response response = client.newCall(request).execute();
       if(response.isSuccessful())
        {
            System.out.println(response.body().toString());
            return response.body().string();
        }
        else return "No Data";



    }

    public String postData(String url, String username, String password, RequestBody body) throws IOException
    {

        client.newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("post", body)
                .addHeader("Authorization", Credentials.basic(username, password))
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful())
        {
            System.out.println(response.body().toString());
            return response.body().string();
        }
        else return "No Data";



    }

}
