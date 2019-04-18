package com.example.fikridzakwan.stadiumclubrestapi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumData;
import com.example.fikridzakwan.stadiumclubrestapi.R;
import com.example.fikridzakwan.stadiumclubrestapi.Model.Constans;
import com.example.fikridzakwan.stadiumclubrestapi.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.ViewHolder> {

    private Context context;
    private List<StadiumData> stadiumDataList;
    private Bundle bundle;

    public StadiumAdapter(Context context, List<StadiumData> stadiumDataList) {
        this.context = context;
        this.stadiumDataList = stadiumDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stadium, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final StadiumData stadiumData = stadiumDataList.get(i);

        viewHolder.txtNamaStadium.setText(stadiumData.getStrStadium());
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(context).load(stadiumData.getStrStadiumThumb()).apply(options).into(viewHolder.imgStadium);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString(Constans.id,stadiumData.getIdTeam());
                context.startActivity(new Intent(context,DetailActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return stadiumDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_stadium)
        ImageView imgStadium;
        @BindView(R.id.txt_nama_stadium)
        TextView txtNamaStadium;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
