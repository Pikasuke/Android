package com.example.newslaterapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newslaterapp.Common.Common;
import com.example.newslaterapp.Interface.IIconBetterIdeaService;
import com.example.newslaterapp.Interface.IItemClickListener;
import com.example.newslaterapp.Model.IconBetterIdea;
import com.example.newslaterapp.Model.Website;
import com.example.newslaterapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    IItemClickListener itemClickListener;
    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(View itemView) {
        super(itemView);
        source_image=(CircleImageView) itemView.findViewById(R.id.source_image);
        source_title=(TextView) itemView.findViewById(R.id.source_name);
    }

    public void setItemClickListener(IItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;
    private Website website;

    private IIconBetterIdeaService mService;

    public ListSourceAdapter(Context context, Website website) {
        this.context = context;
        this.website = website;

        mService = Common.getIconServices();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {
        StringBuilder iconBetterAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        iconBetterAPI.append(website.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterAPI.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                if(response.body().getIcons().size()>0) {
                    Picasso.with(context).load(response.body().getIcons().get(0).getUrl()).into(holder.source_image);
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {

            }
        });


        holder.source_title.setText(website.getSources().get(position).getName());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //IMplemetns in part 2
            }
        });

    }

    @Override
    public int getItemCount() {
        return website.getSources().size();
    }
}
