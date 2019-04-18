package com.example.fikridzakwan.stadiumclubrestapi;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fikridzakwan.stadiumclubrestapi.API.ApiClient;
import com.example.fikridzakwan.stadiumclubrestapi.API.ApiInterface;
import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumData;
import com.example.fikridzakwan.stadiumclubrestapi.Model.Constans;
import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumResponse;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgStadium)
    ImageView imgStadium;
    @BindView(R.id.myToolbar)
    Toolbar myToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.txt_nama_stadium)
    TextView txtNamaStadium;
    @BindView(R.id.txt_detail_stadium)
    TextView txtDetailStadium;

    private Bundle bundle;
    private List<StadiumData> stadiumDataList;
    private ApiInterface apiInterface;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            id = Integer.valueOf(bundle.getString(Constans.id));
        }
        stadiumDataList = new ArrayList<>();

        getData();

    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StadiumResponse> call = apiInterface.getDetailStadium(id);
        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                StadiumResponse stadiumResponse = response.body();
                stadiumDataList = stadiumResponse.getTeams();
                Glide.with(DetailActivity.this).load(stadiumDataList.get(0).getStrStadiumThumb()).into(imgStadium);
                txtNamaStadium.setText(stadiumDataList.get(0).getStrStadium());
                txtDetailStadium.setText(stadiumDataList.get(0).getStrStadiumDescription());
            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
