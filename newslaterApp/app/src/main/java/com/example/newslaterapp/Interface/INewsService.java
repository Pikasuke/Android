package com.example.newslaterapp.Interface;

import com.example.newslaterapp.Model.Website;

import retrofit2.Call;
import retrofit2.http.GET;

public interface INewsService {

    @GET("v2/sources?language=fr")
    Call<Website> getSources();
}
