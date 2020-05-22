package com.example.newslaterapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IconBetterIdeaClient {

    private static Retrofit retrofit=null;
    //Singleton de connexion
    public static Retrofit getClient(){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl("https://i.olsh.me/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
