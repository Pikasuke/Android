package com.example.newslaterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.newslaterapp.Adapter.ListSourceAdapter;
import com.example.newslaterapp.Common.Common;
import com.example.newslaterapp.Interface.INewsService;
import com.example.newslaterapp.Model.Website;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView lisWebsite;
    RecyclerView.LayoutManager layoutManager;
    INewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init cache
        Paper.init(this);
        //Init Service
        mService = Common.getNewsServices();
        //Init View
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });
        lisWebsite = (RecyclerView)findViewById(R.id.list_source);
        lisWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lisWebsite.setLayoutManager((layoutManager));

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);

    }

    private void loadWebsiteSource(boolean isRefreshed){
        if(!isRefreshed) {
            String cache = Paper.book().read("cache");
            //Si il y a un cache
            if (cache != null && !cache.isEmpty()) {
                Website website = new Gson().fromJson(cache, Website.class); // Convert cache de Json vers Object
                adapter = new ListSourceAdapter(getBaseContext(), website);
                adapter.notifyDataSetChanged();
                lisWebsite.setAdapter(adapter);
            } else {
                dialog.show();
                //Fetch new data
                mService.getSources().enqueue(new Callback<Website>() {
                    @Override
                    public void onResponse(Call<Website> call, Response<Website> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        lisWebsite.setAdapter(adapter);

                        //SAve to Cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<Website> call, Throwable t) {

                    }
                });
            }
        }// If from swipe refresh
        else {
            dialog.show();
            //Fetch new data
            mService.getSources().enqueue(new Callback<Website>() {
                @Override
                public void onResponse(Call<Website> call, Response<Website> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    lisWebsite.setAdapter(adapter);

                    //SAve to Cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));
                    //Dismmiss Refresh
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<Website> call, Throwable t) {

                }
            });
        }
    }
}
