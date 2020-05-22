package com.example.newslaterapp.Interface;

import com.example.newslaterapp.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IIconBetterIdeaService {

    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);

}

