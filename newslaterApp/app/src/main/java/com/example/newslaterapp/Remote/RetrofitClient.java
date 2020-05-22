package com.example.newslaterapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit=null;

    //Singleton de connexion
    public static Retrofit getClient(String baseUrl){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
