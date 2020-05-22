package com.example.newslaterapp.Common;

import com.example.newslaterapp.Interface.INewsService;
import com.example.newslaterapp.Interface.IIconBetterIdeaService;
import com.example.newslaterapp.Remote.IconBetterIdeaClient;
import com.example.newslaterapp.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public static final String API_KEY="d9d2731c9dda4721bf0fcba2cb5e599d";

    public static INewsService getNewsServices(){
        return RetrofitClient.getClient(BASE_URL).create(INewsService.class);
    }

    public static IIconBetterIdeaService getIconServices(){
        return IconBetterIdeaClient.getClient().create(IIconBetterIdeaService.class);
    }


}
